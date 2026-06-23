package com.airobosoft.controller.admin;

import com.airobosoft.dto.TrainDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
public class TrainController {

    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO) {

        System.out.println(trainDTO.getTrainNo());
        return new ResponseEntity<>(trainDTO, HttpStatus.OK);

    }
}
