package com.backend.user.controller;

import com.backend.user.model.dto.UserDTO;
import com.backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-user/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new User
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, @RequestParam String sourceApp) {

        UserDTO createdUser = userService.createUser(userDTO, sourceApp);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Get all Users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userDTOS);
    }

    // Get a User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        Optional<UserDTO> userDTOOptional = userService.getUserById(id);
        return userDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing User
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUser = userService.updateUser(id, userDTO);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a User by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
