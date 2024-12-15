package com.rubinho.lab1.transactions;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class TransactionData {
    private List<ProductDto> productsDto;
    private MultipartFile file;
    private User user;
    private TestingExceptions testingExceptions;
}
