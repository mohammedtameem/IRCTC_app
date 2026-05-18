package com.airobosoft.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadFile(
            MultipartFile file
    ) throws Exception;

    Resource downloadFile(
            String fileName
    ) throws Exception;
}