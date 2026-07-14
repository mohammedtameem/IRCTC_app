package com.airobosoft.service;

import com.airobosoft.dto.TrainSeatDTO;

import java.util.List;

public interface TrainSeatService {
    TrainSeatDTO createSeatInfo(TrainSeatDTO trainSeatDTO);
    List<TrainSeatDTO> getSeatInfoByTrainScheduleId(Long scheduleId);
    void deleteSeatInfo(Long seatId);
    TrainSeatDTO updateSeatInfo(Long seatId, TrainSeatDTO trainSeatDTO);
    List<Integer> bookSeat(int seatToBook, Long seatId);
}
