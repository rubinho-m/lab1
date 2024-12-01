package com.rubinho.lab1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rubinho.lab1.dto.ProductDto;

import java.util.List;

public interface ConverterService {
    List<ProductDto> toList(String content) throws JsonProcessingException;
}
