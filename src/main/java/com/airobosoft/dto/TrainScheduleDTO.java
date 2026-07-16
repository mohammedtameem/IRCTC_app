package com.airobosoft.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainScheduleDTO {
    private Long id;
    private Long trainId;
    private LocalDate runningDate;
    private Integer availableSeats;

}
