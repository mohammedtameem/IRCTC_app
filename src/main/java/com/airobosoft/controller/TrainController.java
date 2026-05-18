package com.airobosoft.controller;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService service;

    @Operation(
            summary = "Get All Trains",
            description = "Returns paginated train list"
    )    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<TrainDTO>> listTrain(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        return ResponseEntity.ok(
                service.all(page, size, sortBy, direction)
        );
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
    public ResponseEntity<TrainDTO> getTrainById(
            @PathVariable Long id) {

        TrainDTO train = service.get(id);

        return ResponseEntity.ok(train);
    }

    @DeleteMapping("/{trainNo}")
    public ResponseEntity<Void> deleteByTrainId(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}