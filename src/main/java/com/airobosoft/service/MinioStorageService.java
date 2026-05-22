package com.airobosoft.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioStorageService
        implements StorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(
            MultipartFile file
    ) throws Exception {

        String generatedFileName =

                UUID.randomUUID()
                        + "_"
                        + file.getOriginalFilename();

        minioClient.putObject(

                PutObjectArgs.builder()

                        .bucket(bucketName)

                        .object(generatedFileName)

                        .stream(
                                file.getInputStream(),
                                file.getSize(),
                                -1
                        )

                        .contentType(
                                file.getContentType()
                        )

                        .build()
        );

        return generatedFileName;
    }

    @Override
    public Resource downloadFile(
            String fileName
    ) throws Exception {

        return new InputStreamResource(

                minioClient.getObject(

                        GetObjectArgs.builder()

                                .bucket(bucketName)

                                .object(fileName)

                                .build()
                )
        );
    }
}