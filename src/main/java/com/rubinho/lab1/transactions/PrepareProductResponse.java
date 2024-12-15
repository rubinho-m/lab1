package com.rubinho.lab1.transactions;

import com.rubinho.lab1.dto.ProductDto;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

public record PrepareProductResponse(boolean status,
                                     TransactionStatus transactionStatus,
                                     List<ProductDto> createdProducts) {
}
