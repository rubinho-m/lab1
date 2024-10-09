package com.rubinho.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String zipCode;
    private LocationDto town;
}
