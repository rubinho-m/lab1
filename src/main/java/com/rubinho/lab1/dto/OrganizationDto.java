package com.rubinho.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class OrganizationDto {
    private long id;
    private String name;
    private AddressDto officialAddress;
    private Integer annualTurnover;
    private Long employeesCount;
    private String fullName;
    private Integer rating;
}
