package com.airobosoft.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTrainSearchRequest {
    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDate journeyDate;
}
