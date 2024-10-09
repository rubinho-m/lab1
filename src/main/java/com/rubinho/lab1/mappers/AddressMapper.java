package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.AddressDto;
import com.rubinho.lab1.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);

    AddressDto toDto(Address address);
}
