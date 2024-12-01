package com.rubinho.lab1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rubinho.lab1.dto.AddressDto;
import com.rubinho.lab1.dto.CoordinatesDto;
import com.rubinho.lab1.dto.LocationDto;
import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Color;
import com.rubinho.lab1.model.Country;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.services.impl.ConverterServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterServiceTest {
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
    private final ConverterService converterService = new ConverterServiceImpl(objectMapper);

    @Test
    @DisplayName("Список продуктов должен распарситься из строки в список")
    void yamlParsingTest() throws IOException {
        final String content = Files.readString(Path.of("src/test/resources/products.yaml"));
        final List<ProductDto> expected = List.of(
                new ProductDto(
                        0,
                        "product",
                        null,
                        new CoordinatesDto(
                                null,
                                21L,
                                3.0f
                        ),
                        UnitOfMeasure.CENTIMETERS,
                        new OrganizationDto(
                                0L,
                                "org",
                                new AddressDto(
                                        1L,
                                        "456123",
                                        new LocationDto(
                                                1L,
                                                22,
                                                2.0f,
                                                3,
                                                "town"
                                        )
                                ),
                                1,
                                100L,
                                "organization",
                                100
                        ),
                        500,
                        1111,
                        100.0,
                        new PersonDto(
                                1L,
                                "Mike",
                                Color.BLUE,
                                Color.WHITE,
                                new LocationDto(
                                        2L,
                                        22,
                                        6.0f,
                                        7,
                                        "home"
                                ),
                                183,
                                Country.UNITED_KINGDOM
                        ),
                        null,
                        0
                )
        );

        assertEquals(expected, converterService.toList(content));
    }
}
