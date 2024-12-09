package com.backend.user.service;

import com.backend.user.model.Entity.City;
import com.backend.user.model.Mapper.CityMapper;
import com.backend.user.model.dto.CityDTO;
import com.backend.user.model.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        System.out.println("updateCity called with id: " + id + " and cityDTO: " + cityDTO);

        return cityRepository.findById(id).map(existingCity -> {
            cityMapper.partialUpdate(cityDTO, existingCity);
            City updatedCity = cityRepository.save(existingCity);
            return cityMapper.toDTO(updatedCity);
        });
    }


    // get a City by postCode and inseeCode
    public ResponseEntity<CityDTO> findByPostCodeAndInseeCode(String postCode, String inseeCode) {

        Optional<City> city = cityRepository.findByPostcodeAndInseecode(postCode, inseeCode);

        return city.map(
                        value -> new ResponseEntity<>(cityMapper.toDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Delete a City by ID
    public boolean deleteCity(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // check if city exists in bdd or create it
    public CityDTO findOrCreateCity(String postCode, String inseeCode, String cityName) {

        Optional<City> optionalCity = cityRepository.findByPostcodeAndInseecode(postCode, inseeCode);
        City city;

        // if City is existing, get it
        if (optionalCity.isPresent()) {

            city = optionalCity.get();

            // if city doesn't exist, create it in bdd
        } else {

            city = new City();
            city.setPostcode(postCode);
            city.setInseecode(inseeCode);
            city.setCityname(cityName);

            city = cityRepository.save(city);
        }

        // return City
        return cityMapper.toDTO(city);
    }
}
