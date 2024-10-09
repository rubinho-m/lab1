package com.rubinho.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LocationDto {
    private Long id;
    private Integer x;
    private Float y;
    private int z;
    private String name;
}
