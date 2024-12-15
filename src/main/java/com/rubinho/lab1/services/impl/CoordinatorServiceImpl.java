package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.exceptions.TwoPhaseCommitException;
import com.rubinho.lab1.services.CoordinatorService;
import com.rubinho.lab1.services.ProductService;
import com.rubinho.lab1.services.S3Service;
import com.rubinho.lab1.transactions.PrepareProductResponse;
import com.rubinho.lab1.transactions.PrepareS3Response;
import com.rubinho.lab1.transactions.TestingExceptions;
import com.rubinho.lab1.transactions.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoordinatorServiceImpl implements CoordinatorService {
    private final ProductService productService;
    private final S3Service s3Service;

    @Autowired
    public CoordinatorServiceImpl(ProductService productService, S3Service s3Service) {
        this.productService = productService;
        this.s3Service = s3Service;
    }

    @Override
    public List<ProductDto> twoPhaseCommit(TransactionData transactionData) {
        final UUID tid = UUID.randomUUID();
        try {
            final PrepareResponses responses = callPrepare(transactionData, tid);
            final PrepareProductResponse prepareProductResponse = responses.productResponse();
            final PrepareS3Response prepareS3Response = responses.s3Response();
            if (prepareProductResponse.status() && prepareS3Response.status()) {
                if (callCommit(tid)) {
                    return prepareProductResponse.createdProducts();
                }
            }
        } catch (Exception e) {
            callRollback(tid);
            throw new TwoPhaseCommitException("Couldn't perform 2pc");
        }
        callRollback(tid);
        throw new TwoPhaseCommitException("Couldn't perform 2pc");
    }

    private PrepareResponses callPrepare(TransactionData transactionData,
                                         UUID tid) {
        final TestingExceptions testingExceptions = transactionData.getTestingExceptions();
        final PrepareProductResponse prepareProductResponse = productService.prepareCreateProducts(
                tid,
                transactionData.getProductsDto(),
                transactionData.getUser(),
                testingExceptions.dbException()
        );
        if (testingExceptions.runtimeException()) {
            throw new RuntimeException();
        }
        final PrepareS3Response prepareS3Response = s3Service.prepareUpload(
                tid,
                transactionData.getFile(),
                testingExceptions.s3Exception()
        );
        return new PrepareResponses(prepareProductResponse, prepareS3Response);
    }

    private boolean callCommit(UUID tid) {
        return productService.commit(tid) && s3Service.commit(tid);
    }

    private void callRollback(UUID tid) {
        productService.rollback(tid);
        s3Service.rollback(tid);
    }

    private record PrepareResponses(PrepareProductResponse productResponse, PrepareS3Response s3Response) {
    }
}
