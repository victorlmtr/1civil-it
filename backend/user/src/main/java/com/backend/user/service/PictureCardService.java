package com.backend.user.service;

import com.backend.user.model.Entity.PictureCard;
import com.backend.user.model.Mapper.PictureCardMapper;
import com.backend.user.model.dto.PictureCardDTO;
import com.backend.user.model.repository.PictureCardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PictureCardService {

    @Autowired
    private PictureCardRepository pictureCardRepository;

    @Autowired
    private PictureCardMapper pictureCardMapper;

    // Create a new PictureCard
    public PictureCardDTO createPictureCard(PictureCardDTO pictureCardDTO) {
        PictureCard pictureCard = pictureCardMapper.toEntity(pictureCardDTO);
        pictureCard = pictureCardRepository.save(pictureCard);
        return pictureCardMapper.toDTO(pictureCard);
    }

    // Get all PictureCards
    public List<PictureCardDTO> getAllPictureCards() {
        return pictureCardRepository.findAll().stream()
                .map(pictureCardMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a PictureCard by ID
    public Optional<PictureCardDTO> getPictureCardById(Integer id) {
        return pictureCardRepository.findById(id).map(pictureCardMapper::toDTO);
    }

    // Update an existing PictureCard
    public Optional<PictureCardDTO> updatePictureCard(Integer id, PictureCardDTO pictureCardDTO) {
        return pictureCardRepository.findById(id).map(existingPictureCard -> {
            pictureCardMapper.partialUpdate(pictureCardDTO, existingPictureCard);
            PictureCard updatedPictureCard = pictureCardRepository.save(existingPictureCard);
            return pictureCardMapper.toDTO(updatedPictureCard);
        });
    }

    // Delete a PictureCard by ID
    public boolean deletePictureCard(Integer id) {
        if (pictureCardRepository.existsById(id)) {
            pictureCardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
