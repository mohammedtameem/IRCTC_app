package com.airobosoft.service.impl;

import com.airobosoft.dto.TrainScheduleDTO;
import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainSchedule;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.repo.TrainScheduleRepository;
import com.airobosoft.service.TrainScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainScheduleServiceImpl implements TrainScheduleService {

    private final TrainScheduleRepository trainScheduleRepository;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;


    @Override
    public TrainScheduleDTO createSchedule(TrainScheduleDTO trainScheduleDTO) {
        Train train = trainRepository.findById(trainScheduleDTO.getTrainId()).orElseThrow(() -> new RuntimeException("Train not found with id"+ trainScheduleDTO.getTrainId()));
        TrainSchedule trainSchedule = modelMapper.map(trainScheduleDTO, TrainSchedule.class);
        trainSchedule.setTrain(train);
        TrainSchedule savedTrainSchedule = trainScheduleRepository.save(trainSchedule);
        return modelMapper.map(savedTrainSchedule, TrainScheduleDTO.class);
    }

    @Override
    public List<TrainScheduleDTO> getTrainScheduleByTrainId(Long trainId) {
        List<TrainSchedule> trainSchedules = trainScheduleRepository.findByTrainId(trainId);
        return trainSchedules.stream().map(trainSchedule -> modelMapper.map(trainSchedule, TrainScheduleDTO.class)).toList();
    }

    @Override
    public void deleteTrainSchedule(Long trainScheduleId) {
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new RuntimeException("Train schedule not found with id"+ trainScheduleId));
        trainScheduleRepository.delete(trainSchedule);
    }

    @Override
    public TrainScheduleDTO updateTrainSchedule(TrainScheduleDTO scheduleDTO, Long trainScheduleId) {
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new RuntimeException("Train Schedule not found with id"+ trainScheduleId));
        Train train = trainRepository.findById(scheduleDTO.getTrainId()).orElseThrow(() -> new RuntimeException("Train not found with id"+ scheduleDTO.getTrainId()));
        trainSchedule.setTrain(train);
        trainSchedule.setAvailableSeats(scheduleDTO.getAvailableSeats());
        trainSchedule.setRunningDate(scheduleDTO.getRunningDate());
        return modelMapper.map(trainScheduleRepository.save(trainSchedule),  TrainScheduleDTO.class);
    }
}
