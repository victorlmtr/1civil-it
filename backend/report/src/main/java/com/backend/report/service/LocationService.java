package com.backend.report.service;

import com.backend.report.model.dto.LocationDto;
import com.backend.report.model.entity.Location;
import com.backend.report.model.mapper.LocationMapper;
import com.backend.report.model.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    // Create a new Location
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = locationMapper.toEntity(locationDto);
        location = locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    // Get all Locations
    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a Location by ID
    public Optional<LocationDto> getLocationById(Integer id) {
        return locationRepository.findById(id).map(locationMapper::toDto);
    }

    // Update an existing Location
    public Optional<LocationDto> updateLocation(Integer id, LocationDto locationDto) {
        return locationRepository.findById(id).map(existingLocation -> {
            locationMapper.partialUpdate(locationDto, existingLocation);
            Location updatedLocation = locationRepository.save(existingLocation);
            return locationMapper.toDto(updatedLocation);
        });
    }

    // Delete a Location by ID
    public boolean deleteLocation(Integer id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

