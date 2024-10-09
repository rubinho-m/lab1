package com.rubinho.lab1.dto;

import com.rubinho.lab1.model.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private LocalDate creationDate;
    private CoordinatesDto coordinates;
    private UnitOfMeasure unitOfMeasure;
    private OrganizationDto manufacturer;
    private int price;
    private long manufactureCost;
    private Double rating;
    private PersonDto owner;
}
