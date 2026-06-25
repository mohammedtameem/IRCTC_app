package com.airobosoft.controller.admin;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.entity.Train;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    @GetMapping()
    @Operation(summary = "Get all Trains", description = "Returns paginated list of trains")
    public ResponseEntity<Page<TrainDTO>> getAllTrains(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "trainNo")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ){
        return ResponseEntity.ok(trainService.all(page, size, sortBy, direction));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add more than one train record at a time")
    public ResponseEntity<List<TrainDTO>> addMultipleTrains(@RequestBody List<@Valid TrainDTO> train){
        return new ResponseEntity<>(trainService.addTrainBulk(train), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO) {

        System.out.println(trainDTO.getTrainNo());
        return new ResponseEntity<>(trainDTO, HttpStatus.OK);

    }

    @GetMapping
    public TrainDTO getTrain(Long id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("with train id : " + id));
        return modelMapper.map(train, TrainDTO.class);
    }

    @DeleteMapping
    public void deleteTrain(Long id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Train not found with train id : "+ id));
        trainRepository.delete(train);
    }
}
