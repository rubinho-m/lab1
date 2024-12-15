package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.transactions.TransactionData;

import java.util.List;

public interface CoordinatorService {
    List<ProductDto> twoPhaseCommit(TransactionData transactionData);
}
