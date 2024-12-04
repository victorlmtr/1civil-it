package com.backend.user.controller;

import com.backend.user.model.Entity.City;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-user/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Create a new City
    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        CityDTO createdCity = cityService.createCity(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    // Get all Cities
    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cityDTOS = cityService.getAllCities();
        return ResponseEntity.ok(cityDTOS);
    }

    // Get a City by ID
    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Integer id) {
        Optional<CityDTO> cityDTOOptional = cityService.getCityById(id);
        return cityDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing City
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
        Optional<CityDTO> updatedCity = cityService.updateCity(id, cityDTO);
        return updatedCity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a City by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
        boolean deleted = cityService.deleteCity(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<CityDTO> getByPostCodeAndInseeCode(@RequestParam String postCode, @RequestParam String inseeCode) {

        return cityService.findByPostCodeAndInseeCode(postCode, inseeCode);
    }
}
