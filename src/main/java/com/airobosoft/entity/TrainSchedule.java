package com.airobosoft.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="train_schedule")
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate runningDate;

    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;

    private Integer availableSeats;


}
