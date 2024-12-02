package com.backend.report.controller;

import com.backend.report.model.dto.PictureDto;
import com.backend.report.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports/pictures")
public class PictureController {

    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    // Create a new Picture
    @PostMapping
    public ResponseEntity<PictureDto> createPicture(@RequestBody PictureDto pictureDto) {
        PictureDto createdPicture = pictureService.createPicture(pictureDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPicture);
    }

    // Get all Pictures
    @GetMapping
    public ResponseEntity<List<PictureDto>> getAllPictures() {
        List<PictureDto> pictures = pictureService.getAllPictures();
        return ResponseEntity.ok(pictures);
    }

    // Get a Picture by ID
    @GetMapping("/{id}")
    public ResponseEntity<PictureDto> getPictureById(@PathVariable Integer id) {
        Optional<PictureDto> picture = pictureService.getPictureById(id);
        return picture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Picture
    @PutMapping("/{id}")
    public ResponseEntity<PictureDto> updatePicture(@PathVariable Integer id, @RequestBody PictureDto pictureDto) {
        Optional<PictureDto> updatedPicture = pictureService.updatePicture(id, pictureDto);
        return updatedPicture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Picture by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePicture(@PathVariable Integer id) {
        boolean deleted = pictureService.deletePicture(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

