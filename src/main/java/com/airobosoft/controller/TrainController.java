package com.airobosoft.controller;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService service;

    @GetMapping(path="/list",produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<TrainDTO>> listTrain() {

        return ResponseEntity.ok(service.all());
    }

    @PostMapping
    public ResponseEntity<TrainDTO> addTrain(
            @Valid @RequestBody TrainDTO train) {

        TrainDTO savedTrain = service.add(train);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTrain);
    }

    @GetMapping("/{trainNo}")
    public ResponseEntity<TrainDTO> getTrainByNo(
            @PathVariable String trainNo) {

        TrainDTO train = service.get(trainNo);

        return ResponseEntity.ok(train);
    }

    @DeleteMapping("/{trainNo}")
    public ResponseEntity<Void> deleteTrainNo(
            @PathVariable String trainNo) {

        service.delete(trainNo);

        return ResponseEntity.noContent().build();
    }
}