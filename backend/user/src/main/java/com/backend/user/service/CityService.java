package com.backend.user.service;

import com.backend.user.model.Entity.City;
import com.backend.user.model.Mapper.CityMapper;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.model.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    // Create a new City
    public CityDTO createCity(CityDTO cityDTO) {
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDTO(city);
    }

    // Get all Cities
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a City by ID
    public Optional<CityDTO> getCityById(Integer id) {
        return cityRepository.findById(id).map(cityMapper::toDTO);
    }

    // Update an existing City
    public Optional<CityDTO> updateCity(Integer id, CityDTO cityDTO) {
        return cityRepository.findById(id).map(existingCity -> {
            cityMapper.partialUpdate(cityDTO, existingCity);
            City updatedCity = cityRepository.save(existingCity);
            return cityMapper.toDTO(updatedCity);
        });
    }

    // Delete a City by ID
    public boolean deleteCity(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
