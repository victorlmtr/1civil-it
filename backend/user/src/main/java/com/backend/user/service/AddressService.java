package com.backend.user.service;

import com.backend.user.model.Entity.Address;
import com.backend.user.model.Mapper.AddressMapper;
import com.backend.user.model.dto.AddressDTO;
import com.backend.user.model.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    // Create a new Address
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    // Get all Addresses
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get an Address by ID
    public Optional<AddressDTO> getAddressById(Integer id) {
        return addressRepository.findById(id).map(addressMapper::toDTO);
    }

    // Update an existing Address
    public Optional<AddressDTO> updateAddress(Integer id, AddressDTO addressDTO) {
        return addressRepository.findById(id).map(existingAddress -> {
            addressMapper.partialUpdate(addressDTO, existingAddress);
            Address updatedAddress = addressRepository.save(existingAddress);
            return addressMapper.toDTO(updatedAddress);
        });
    }

    // Delete a Address by ID
    public boolean deleteAddress(Integer id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
