package com.rubinho.lab1.transactions;

import com.rubinho.lab1.dto.ProductDto;

import java.util.List;

public record PrepareProductResponse(boolean status,
                                     List<ProductDto> createdProducts) {
}
