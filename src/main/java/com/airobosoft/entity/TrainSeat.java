package com.airobosoft.entity;

import com.airobosoft.enums.CoachType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="train_seats")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="train_schedule_id")
    private TrainSchedule trainSchedule;

    @Enumerated(EnumType.STRING)
    private CoachType coachType;

    private Integer totalSeats;

    private Integer availableSeats;

    private Integer nextToAssign=10;

    private Double price;

    private Integer seatNumberToAssign;

    private Integer seatOrder;

    public boolean isCoachfull(){
        return availableSeats<=0;
    }

    public boolean isSeatAvailable(int seatToBook){
        return seatToBook <= availableSeats;
    }



}
