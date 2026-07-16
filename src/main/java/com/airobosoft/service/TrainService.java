package com.airobosoft.service;

import com.airobosoft.dto.AvailableTrainResponse;
import com.airobosoft.dto.TrainDTO;
import com.airobosoft.dto.UserTrainSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;


@Service
public interface TrainService {

    TrainDTO add(TrainDTO train);
    List<TrainDTO> addTrainBulk(List<TrainDTO> trainDtos);
    Page<TrainDTO> all(int page, int size, String sortBy, String direction);
    TrainDTO getTrain(Long id);
    ByteArrayInputStream exportTrainsToExcel();
    void sendTrainReport(String email);
    List<AvailableTrainResponse> userSearchTrains(UserTrainSearchRequest searchRequest);

}