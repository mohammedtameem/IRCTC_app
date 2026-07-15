package com.airobosoft.service;

import com.airobosoft.dto.TrainScheduleDTO;

import java.util.List;

public interface TrainScheduleService {
    TrainScheduleDTO createSchedule(TrainScheduleDTO scheduleDTO);
    List<TrainScheduleDTO> getTrainScheduleByTrainId(Long trainId);
    void deleteTrainSchedule(Long trainScheduleId);
    TrainScheduleDTO updateTrainSchedule(TrainScheduleDTO scheduleDTO,  Long trainScheduleId);
}
