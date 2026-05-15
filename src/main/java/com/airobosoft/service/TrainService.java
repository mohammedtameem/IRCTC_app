package com.airobosoft.service;

import com.airobosoft.entity.Train;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    List<Train> trainList = new ArrayList<>();

    public TrainService() {

        trainList.add(new Train("1234","Bangalore - chennai superfast",20));
        trainList.add(new Train("1235","Chennai - Bangalore shatabdi",10));

    }

    public Train add(Train train) {
        trainList.add(train);
        return train;
    }

    public List<Train> all() {
        return this.trainList;
    }

    public Train get(String trainNo) {
       return trainList.stream().filter(train -> train.trainNo().equals(trainNo))
                .findFirst().get();
    }

    public void delete(String trainNo) {
        List<Train> list = this.trainList.stream().filter(train -> !train.trainNo().equals(trainNo))
                .toList();

        this.trainList = list;
    }




}
