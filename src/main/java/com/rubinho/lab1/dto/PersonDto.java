package com.rubinho.lab1.dto;

import com.rubinho.lab1.model.Color;
import com.rubinho.lab1.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private Color eyeColor;
    private Color hairColor;
    private LocationDto location;
    private Integer height;
    private Country nationality;
}
