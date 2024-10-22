package com.back.address.model.Mapper;

import com.back.address.model.Entity.Address;
import com.back.address.model.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // Convert Address entity to AddressDTO
    public AddressDTO toDto(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressdto = new AddressDTO();

        addressdto.setId(address.getId());
        addressdto.setLatitude(address.getLatitude());
        addressdto.setLongitude(address.getLongitude());
        addressdto.setAddress(address.getAddress());

        return addressdto;
    }

    // Convert AddressDTO to Address entity
    public Address toEntity(AddressDTO addressdto) {
        if (addressdto == null) {
            return null;
        }

        Address address = new Address();

        address.setId(addressdto.getId());
        address.setLatitude(addressdto.getLatitude());
        address.setLongitude(addressdto.getLongitude());
        address.setAddress(addressdto.getAddress());
        return address;
    }
}