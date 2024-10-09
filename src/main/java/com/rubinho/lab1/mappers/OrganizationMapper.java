package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.model.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface OrganizationMapper {
    Organization toEntity(OrganizationDto organizationDto);

    OrganizationDto toDto(Organization organization);
}
