package com.airobosoft.service;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.entity.Train;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.reports.ExcelGenerator;
import com.airobosoft.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public TrainDTO add(TrainDTO train) {

        Train trainEntity = modelMapper.map(train, Train.class);
        return modelMapper.map(trainRepository.save(trainEntity), TrainDTO.class);
    }

    public List<TrainDTO> addTrainBulk(List<TrainDTO> trainDtos){

        List<Train> trains = trainDtos.stream()
                .map(dto ->
                        modelMapper.map(
                                dto,
                                Train.class
                        ))
                .toList();

        List<Train> savedTrains =
                trainRepository.saveAll(trains);

        return savedTrains.stream()
                .map(train ->
                        modelMapper.map(
                                train,
                                TrainDTO.class
                        ))
                .toList();
    }
    // SELECT *
    public Page<TrainDTO> all(int page,
                              int size,
                              String sortBy,
                              String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Train> trainPage =
                trainRepository.findAll(pageable);

        return trainPage.map(train ->
                modelMapper.map(train, TrainDTO.class));
    }

    public TrainDTO getTrain(Long id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("with train id : " + id));
        return modelMapper.map(train, TrainDTO.class);
    }




    public ByteArrayInputStream exportTrainsToExcel() {

        List<TrainDTO> trains =
                trainRepository.findAll()
                        .stream()
                        .map(train ->
                                modelMapper.map(
                                        train,
                                        TrainDTO.class
                                ))
                        .toList();

        return ExcelGenerator.generateExcel(trains);
    }

    public void sendTrainReport(String email) {

        List<TrainDTO> trains =
                trainRepository.findAll()
                        .stream()
                        .map(train ->
                                modelMapper.map(
                                        train,
                                        TrainDTO.class
                                ))
                        .toList();

        System.out.println(trains.size());

        ByteArrayInputStream excelFile =
                ExcelGenerator.generateExcel(trains);

        emailService.sendExcelReport(
                email,
                excelFile
        );
    }
}