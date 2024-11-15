package com.backend.user.controller;

import com.backend.user.model.dto.PictureCardDTO;
import com.backend.user.service.PictureCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-user/picture-cards")
public class PictureCardController {

    private final PictureCardService pictureCardService;

    @Autowired
    public PictureCardController(PictureCardService pictureCardService) {
        this.pictureCardService = pictureCardService;
    }

    // Create a new PictureCard
    @PostMapping
    public ResponseEntity<PictureCardDTO> createPictureCard(@RequestBody PictureCardDTO pictureCardDTO) {
        PictureCardDTO createdPictureCard = pictureCardService.createPictureCard(pictureCardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPictureCard);
    }

    // Get all PictureCards
    @GetMapping
    public ResponseEntity<List<PictureCardDTO>> getAllPictureCards() {
        List<PictureCardDTO> pictureCardDTOS = pictureCardService.getAllPictureCards();
        return ResponseEntity.ok(pictureCardDTOS);
    }

    // Get a PictureCard by ID
    @GetMapping("/{id}")
    public ResponseEntity<PictureCardDTO> getPictureCardById(@PathVariable Integer id) {
        Optional<PictureCardDTO> pictureCardDTOOptional = pictureCardService.getPictureCardById(id);
        return pictureCardDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing PictureCard
    @PutMapping("/{id}")
    public ResponseEntity<PictureCardDTO> updatePictureCard(@PathVariable Integer id, @RequestBody PictureCardDTO pictureCardDTO) {
        Optional<PictureCardDTO> updatedPictureCard = pictureCardService.updatePictureCard(id, pictureCardDTO);
        return updatedPictureCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a PictureCard by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePictureCard(@PathVariable Integer id) {
        boolean deleted = pictureCardService.deletePictureCard(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
