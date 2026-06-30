package com.airobosoft.dto;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainRouteDto {
    private Long id;
    private TrainDTO train;
    private StationDTO station;
    private Integer stationOrder;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Integer haltMinutes;
    private Integer distanceFromSource;
}

