package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDto personDto);

    PersonDto toDto(Person person);
}
