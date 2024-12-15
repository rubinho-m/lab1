package com.rubinho.lab1.services;

import com.rubinho.lab1.transactions.PrepareS3Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface S3Service {
    PrepareS3Response prepareUpload(UUID tid, MultipartFile file, boolean exception);

    boolean commit(UUID tid);

    void rollback(UUID tid);

    byte[] download(String objName);
}
