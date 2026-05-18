package com.airobosoft.service;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.entity.Train;
import com.airobosoft.repo.TrainRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    public TrainDTO add(TrainDTO train) {

        Train trainEntity = modelMapper.map(train,Train.class);
        return modelMapper.map(trainRepository.save(trainEntity),TrainDTO.class);
    }

    // SELECT *
    public List<TrainDTO> all() {

        return trainRepository.findAll()
                .stream()
                .map(train ->
                        modelMapper.map(train, TrainDTO.class))
                .toList();
    }

    public TrainDTO get(String id) {

        Train train =
                trainRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Train Not Found"));

        return modelMapper.map(train, TrainDTO.class);
    }

    public void delete(String id) {

        trainRepository.deleteById(id);
    }
}