package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.LocationDto;
import com.rubinho.lab1.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(LocationDto locationDto);

    LocationDto toDto(Location location);
}
