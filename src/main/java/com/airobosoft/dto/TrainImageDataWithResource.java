package com.airobosoft.dto;

import org.springframework.core.io.Resource;

public record TrainImageDataWithResource(

        String fileType,

        Resource resource

) {
}