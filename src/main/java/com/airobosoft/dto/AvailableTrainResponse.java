package com.airobosoft.dto;

import com.airobosoft.enums.CoachType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableTrainResponse {
    private Long trainId;
    private String trainNumber;
    private String trainName;
    private LocalDate scheduleDate;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private Map<CoachType, Integer> seatsAvailable;
    private Map<CoachType, Double> priceByCoach;


}
