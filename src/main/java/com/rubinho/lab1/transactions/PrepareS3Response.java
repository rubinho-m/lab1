package com.rubinho.lab1.transactions;

import org.springframework.web.multipart.MultipartFile;

public record PrepareS3Response(boolean status, MultipartFile file) {
}
