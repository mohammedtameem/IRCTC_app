package com.airobosoft.controller.user;

import com.airobosoft.dto.AvailableTrainResponse;
import com.airobosoft.dto.UserTrainSearchRequest;
import com.airobosoft.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/trains")
public class TrainController {

    private final TrainService trainService;

    @PostMapping("/search")
    public ResponseEntity<List<AvailableTrainResponse>> userSearchTrains(@RequestBody UserTrainSearchRequest searchRequest){
        List<AvailableTrainResponse> availableTrainResponses =  trainService.userSearchTrains(searchRequest);
        return new ResponseEntity<>(availableTrainResponses, HttpStatus.OK);
    }
}
