package com.airobosoft.service.impl;

import com.airobosoft.dto.TrainSeatDTO;
import com.airobosoft.entity.TrainSchedule;
import com.airobosoft.entity.TrainSeat;
import com.airobosoft.exception.ResourceNotFoundException;
import com.airobosoft.repo.TrainScheduleRepository;
import com.airobosoft.repo.TrainSeatRepository;
import com.airobosoft.service.TrainSeatService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainSeatServiceImpl implements TrainSeatService {

    private final TrainSeatRepository trainSeatRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public TrainSeatDTO createSeatInfo(TrainSeatDTO trainSeatDTO) {
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainSeatDTO.getTrainScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Train schedule not found with id : "+ trainSeatDTO.getTrainScheduleId()));
        TrainSeat trainSeat = modelMapper.map(trainSeatDTO, TrainSeat.class);
        trainSeat.setTrainSchedule(trainSchedule);
        TrainSeat savedTrainSeat = trainSeatRepository.save(trainSeat);
        return modelMapper.map(savedTrainSeat, TrainSeatDTO.class);
    }

    @Override
    public List<TrainSeatDTO> getSeatInfoByTrainScheduleId(Long scheduleId) {
        trainScheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException("Train schedule not found with id : "+ scheduleId));
        List<TrainSeat> trainSeats = trainSeatRepository.findByTrainScheduleId(scheduleId);
        return trainSeats.stream().map(trainSeat -> modelMapper.map(trainSeat, TrainSeatDTO.class)).toList();
    }

    @Override
    public void deleteSeatInfo(Long seatId) {
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train seat not found with id : "+ seatId));
        trainSeatRepository.delete(trainSeat);
    }

    @Override
    public TrainSeatDTO updateSeatInfo(Long seatId, TrainSeatDTO trainSeatDTO) {
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train seat not found with id : "+ seatId));
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainSeatDTO.getTrainScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Train schedule not found with id : "+ trainSeatDTO.getTrainScheduleId()));
        trainSeat.setTrainSchedule(trainSchedule);
        trainSeat.setCoachType(trainSeatDTO.getCoachType());
        trainSeat.setAvailableSeats(trainSeatDTO.getAvailableSeats());
        trainSeat.setPrice(trainSeatDTO.getPrice());
        trainSeat.setTotalSeats(trainSeatDTO.getTotalSeats());
        trainSeat.setSeatNumberToAssign(trainSeatDTO.getSeatNumberToAssign());
        trainSeat.setSeatOrder(trainSeatDTO.getSeatOrder());
        TrainSeat updatedTrainSeat = trainSeatRepository.save(trainSeat);

        return modelMapper.map(updatedTrainSeat, TrainSeatDTO.class);
    }

    @Override
    @Synchronized
    public List<Integer> bookSeat(int seatToBook, Long seatId){
        TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException("Train seat not found with id:" + seatId));
        if(trainSeat.isSeatAvailable(seatToBook)){
            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats()-seatToBook);
            List<Integer> bookedSeats = new ArrayList<>();
            for(int i=1; i<= seatToBook; i++){
                bookedSeats.add(trainSeat.getSeatNumberToAssign());
                trainSeat.setSeatNumberToAssign(trainSeat.getSeatNumberToAssign()+1);
            }
            trainSeatRepository.save(trainSeat);
            return bookedSeats;
        }else {
            throw new IllegalStateException("No seats available in this coach");
        }
    }
}
