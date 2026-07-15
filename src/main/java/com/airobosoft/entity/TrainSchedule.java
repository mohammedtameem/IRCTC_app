package com.airobosoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="train_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime runningDate;

    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;

    private Integer availableSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<TrainSeat> trainSeats;

    @OneToMany(mappedBy = "trainSchedule")
    private List<Booking> bookings;


}
