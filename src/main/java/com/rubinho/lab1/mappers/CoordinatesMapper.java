package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.CoordinatesDto;
import com.rubinho.lab1.model.Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {
    Coordinates toEntity(CoordinatesDto coordinatesDto);

    CoordinatesDto toDto(Coordinates coordinates);
}
