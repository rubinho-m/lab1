package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.services.S3Service;
import com.rubinho.lab1.transactions.PrepareS3Response;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class S3ServiceImpl implements S3Service {
    private final MinioClient minioClient;
    private final ConcurrentMap<UUID, MultipartFile> fileMap = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();
    private final static String BUCKET = "test";

    @Autowired
    public S3ServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public PrepareS3Response prepareUpload(UUID tid, MultipartFile file, boolean exception) {
        fileMap.put(tid, file);
        lock.lock();
        try {
            if (exception) {
                throw new RuntimeException();
            }
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET).build());
            }
            return new PrepareS3Response(true, file);
        } catch (Exception e) {
            return new PrepareS3Response(false, file);
        }
    }

    @Override
    public boolean commit(UUID tid) {
        final MultipartFile file = fileMap.get(tid);
        final String objName = file.getOriginalFilename();
        try {
            final InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(BUCKET).object(objName).stream(
                                    inputStream, inputStream.available(), -1)
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void rollback(UUID tid) {
        try {
            final String objName = fileMap.get(tid).getOriginalFilename();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET).object(objName).build());
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    }

    @Override
    public byte[] download(String objName) {
        try (InputStream stream =
                     minioClient.getObject(GetObjectArgs
                             .builder()
                             .bucket(BUCKET)
                             .object(objName)
                             .build())) {
            return stream.readAllBytes();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't download file from S3", e);
        }
    }
}
