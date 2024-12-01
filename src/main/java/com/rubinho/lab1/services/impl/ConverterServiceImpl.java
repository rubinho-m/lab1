package com.rubinho.lab1.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.services.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterServiceImpl implements ConverterService {
    private final ObjectMapper objectMapper;

    @Autowired
    public ConverterServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ProductDto> toList(String content) throws JsonProcessingException {
        return objectMapper.readValue(
                content,
                objectMapper.getTypeFactory().constructCollectionType(List.class, ProductDto.class)
        );
    }
}
