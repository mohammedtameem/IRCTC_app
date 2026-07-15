package com.airobosoft.dto;

import com.airobosoft.enums.CoachType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSeatDTO {

    private Long id;
    private Long trainScheduleId;
    private CoachType coachType;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
    private Integer seatNumberToAssign;
    private Integer seatOrder;

}
