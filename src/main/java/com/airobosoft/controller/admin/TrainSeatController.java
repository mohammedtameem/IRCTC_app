package com.airobosoft.controller.admin;

import com.airobosoft.dto.TrainSeatDTO;
import com.airobosoft.service.TrainSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/train-seats")
public class TrainSeatController {
    private final TrainSeatService trainSeatService;

    @PostMapping
    public ResponseEntity<TrainSeatDTO> createSeat(@RequestBody TrainSeatDTO trainSeatDTO){
        return new ResponseEntity<>(trainSeatService.createSeatInfo(trainSeatDTO), HttpStatus.CREATED);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<TrainSeatDTO>> getSeatInfoByTrainScheduleId(@PathVariable Long scheduleId){
        return new ResponseEntity<>(trainSeatService.getSeatInfoByTrainScheduleId(scheduleId), HttpStatus.OK);
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeatInfo(@PathVariable Long seatId){
        trainSeatService.deleteSeatInfo(seatId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<TrainSeatDTO> updateSeatInfo(@PathVariable Long seatId, @RequestBody TrainSeatDTO trainSeatDTO){
        return new ResponseEntity<>(trainSeatService.updateSeatInfo(seatId, trainSeatDTO), HttpStatus.OK);

    }
}
