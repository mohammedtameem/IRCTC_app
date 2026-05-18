package com.airobosoft.service;

import com.airobosoft.dto.TrainDTO;
import com.airobosoft.entity.Train;
import com.airobosoft.repo.TrainRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    public TrainDTO add(TrainDTO train) {

        Train trainEntity = modelMapper.map(train,Train.class);
        return modelMapper.map(trainRepository.save(trainEntity),TrainDTO.class);
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

    public TrainDTO get(Long id) {

        Train train =
                trainRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Train Not Found"));

        return modelMapper.map(train, TrainDTO.class);
    }

    public void delete(Long id) {

        trainRepository.deleteById(id);
    }
}