package com.airobosoft.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainScheduleDTO {
    private Long id;
    private Long trainId;
    private LocalDateTime runningDate;
    private Integer availableSeats;

}
