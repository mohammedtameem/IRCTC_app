package com.airobosoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="train_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate runningDate;

    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;

    private Integer availableSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<TrainSeat> trainSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<Booking> bookings;


}
