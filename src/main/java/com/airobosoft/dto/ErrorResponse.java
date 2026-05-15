package com.airobosoft.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(String message, String code, boolean success, LocalDateTime localDateTime) {
}
