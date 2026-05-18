package com.airobosoft.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainNo;

    private String name;

    private Integer coaches;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TrainImage trainImage;

    public Train() {
    }

    public Train(Long id,
                       String trainNo,
                       String name,
                       Integer coaches) {
        this.id = id;
        this.trainNo = trainNo;
        this.name = name;
        this.coaches = coaches;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoaches() {
        return coaches;
    }

    public void setCoaches(Integer coaches) {
        this.coaches = coaches;
    }

    public TrainImage getTrainImage() {
        return trainImage;
    }

    public void setTrainImage(TrainImage trainImage) {
        this.trainImage = trainImage;
    }
}