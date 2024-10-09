package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class, OrganizationMapper.class, PersonMapper.class})
public interface ProductMapper {
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);
}
