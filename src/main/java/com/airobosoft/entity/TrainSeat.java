package com.airobosoft.entity;

import com.airobosoft.enums.CoachType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="train_seats")
@Data
@Builder
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

    private BigDecimal price;


}
