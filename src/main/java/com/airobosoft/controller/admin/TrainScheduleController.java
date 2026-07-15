package com.airobosoft.controller.admin;

import com.airobosoft.dto.TrainScheduleDTO;
import com.airobosoft.service.TrainScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/train-schedules")
public class TrainScheduleController {
    private final TrainScheduleService trainScheduleService;

    @PostMapping
    public ResponseEntity<TrainScheduleDTO> createSchedule(@RequestBody TrainScheduleDTO trainScheduleDTO) {
        TrainScheduleDTO createdTrainSchedule = trainScheduleService.createSchedule(trainScheduleDTO);
        return new ResponseEntity<>(createdTrainSchedule, HttpStatus.CREATED);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TrainScheduleDTO>> getTrainScheduleByTrainId(@PathVariable Long trainId) {
        List<TrainScheduleDTO> schedules = trainScheduleService.getTrainScheduleByTrainId(trainId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @DeleteMapping("/{trainScheduleId}")
    public ResponseEntity<Void> deleteTrainSchedule(@PathVariable Long trainScheduleId){
         this.trainScheduleService.deleteTrainSchedule(trainScheduleId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{trainScheduleId}")
    public ResponseEntity<TrainScheduleDTO> updateTrainSchedule(@RequestBody TrainScheduleDTO scheduleDTO, @PathVariable Long trainScheduleId){
        TrainScheduleDTO updateTrainSchedule = trainScheduleService.updateTrainSchedule(scheduleDTO, trainScheduleId);
        return new ResponseEntity<>(updateTrainSchedule, HttpStatus.OK);
    }
}
