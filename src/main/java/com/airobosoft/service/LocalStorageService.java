package com.airobosoft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Service
@Primary
public class LocalStorageService
        implements StorageService {

    @Value("${train.image.folder.path}")
    private String folderPath;

    @Override
    public String uploadFile(
            MultipartFile file
    ) throws Exception {

        if (!Files.exists(Path.of(folderPath))) {

            Files.createDirectories(
                    Paths.get(folderPath)
            );
        }

        String generatedFileName =

                UUID.randomUUID()
                        + "_"
                        + file.getOriginalFilename();

        Path uploadPath = Paths.get(
                folderPath,
                generatedFileName
        );

        Files.copy(
                file.getInputStream(),
                uploadPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return generatedFileName;
    }

    @Override
    public Resource downloadFile(
            String fileName
    ) throws Exception {

        Path filePath = Paths.get(
                folderPath,
                fileName
        );

        return new UrlResource(
                filePath.toUri()
        );
    }
}