package com.airobosoft.dto;

import com.airobosoft.entity.TrainImage;

import java.time.LocalDateTime;

public record TrainImageResponse(
        Long id,
        String fileName,
        String fileType,
        String url,
        long size,
        LocalDateTime uploadTime
)   {

    public static TrainImageResponse from(TrainImage image,String baseUrl, Long id) {
        return new TrainImageResponse(image.getId(),
                image.getFileName(),
                image.getFileType(),
                baseUrl + "/trains/" + id + "/image",
                image.getSize(),image.getUploadTime()
        );

    }
}
