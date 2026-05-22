package com.airobosoft.entity;

import com.airobosoft.enums.CoachType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="train_seats")
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

    private BigDecimal price;


}
