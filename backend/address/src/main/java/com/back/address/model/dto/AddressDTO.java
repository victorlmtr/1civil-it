package com.back.address.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddressDTO {
    private Integer id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
}