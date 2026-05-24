package com.airobosoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "trains")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainNo;

    private String name;

    private Integer totalDistance;

    @ManyToOne
    @JoinColumn(name="source_station_id")
    private Station sourceStation;

    @ManyToOne
    @JoinColumn(name="destination_station_id")
    private Station destinationStation;

    @OneToMany(mappedBy = "train")
    private List<TrainRoute> routes;

    @OneToMany(mappedBy = "train")
    private List<TrainSchedule> schedules;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="train_image_id" +
            "")
    private TrainImage trainImage;

}