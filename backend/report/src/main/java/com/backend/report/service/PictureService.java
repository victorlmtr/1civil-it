package com.backend.report.service;

import com.backend.report.model.dto.PictureDto;
import com.backend.report.model.entity.Picture;
import com.backend.report.model.mapper.PictureMapper;
import com.backend.report.model.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private PictureMapper pictureMapper;

    // Create a new Picture
    public PictureDto createPicture(PictureDto pictureDto) {
        Picture picture = pictureMapper.toEntity(pictureDto);
        picture = pictureRepository.save(picture);
        return pictureMapper.toDto(picture);
    }

    // Get all Pictures
    public List<PictureDto> getAllPictures() {
        return pictureRepository.findAll().stream()
                .map(pictureMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a Picture by ID
    public Optional<PictureDto> getPictureById(Integer id) {
        return pictureRepository.findById(id).map(pictureMapper::toDto);
    }

    // Update an existing Picture
    public Optional<PictureDto> updatePicture(Integer id, PictureDto pictureDto) {
        return pictureRepository.findById(id).map(existingPicture -> {
            pictureMapper.partialUpdate(pictureDto, existingPicture);
            Picture updatedPicture = pictureRepository.save(existingPicture);
            return pictureMapper.toDto(updatedPicture);
        });
    }

    // Delete a Picture by ID
    public boolean deletePicture(Integer id) {
        if (pictureRepository.existsById(id)) {
            pictureRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
