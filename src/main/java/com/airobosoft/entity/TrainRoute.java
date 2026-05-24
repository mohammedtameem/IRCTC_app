package com.airobosoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name="train_routes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name="station_id")
    private Station station;

    private Integer stationOrder;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    private Integer haltMinutes;

    private Integer distanceFromSource;
}
