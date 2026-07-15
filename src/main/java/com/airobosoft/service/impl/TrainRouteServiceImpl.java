package com.airobosoft.service.impl;

import com.airobosoft.dto.TrainRouteDto;
import com.airobosoft.entity.Station;
import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainRoute;
import com.airobosoft.exception.ResourceNotFoundException;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.repo.TrainRouteRepository;
import com.airobosoft.repo.admin.StationRepository;
import com.airobosoft.service.TrainRouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainRouteServiceImpl implements TrainRouteService {

    private final TrainRouteRepository trainRouteRepository;
    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;


    @Override
    public TrainRouteDto addRoute(TrainRouteDto trainRouteDto) {

        Train train = this.trainRepository.findById(trainRouteDto.getTrain().getId()).orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainRouteDto.getTrain().getId())) ;
        Station station = this.stationRepository.findById(trainRouteDto.getStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with id: " + trainRouteDto.getStation().getId()));

        TrainRoute trainRoute = modelMapper.map(trainRouteDto, TrainRoute.class);

        trainRoute.setTrain(train);
        trainRoute.setStation(station);

        TrainRoute trainRouteSaved = this.trainRouteRepository.save(trainRoute);

        return modelMapper.map(trainRouteSaved, TrainRouteDto.class);
    }

    @Override
    public List<TrainRouteDto> getRoutesByTrainId(Long trainId) {
        Train train = this.trainRepository.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainId));
        List<TrainRoute> trainRoutes = this.trainRouteRepository.findByTrain(train);

        return trainRoutes.stream().map(trainRoute -> modelMapper.map(trainRoute, TrainRouteDto.class)).toList();
    }

    @Override
    public TrainRouteDto updateRoute(TrainRouteDto trainRouteDto, Long id) {
        TrainRoute existingRoute = this.trainRouteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train Route not found with id: " + id));
        Train train = this.trainRepository.findById(trainRouteDto.getTrain().getId()).orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + trainRouteDto.getTrain().getId()));
        Station station = this.stationRepository.findById(trainRouteDto.getStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Station not found with id: " + trainRouteDto.getStation().getId()));

        existingRoute.setTrain(train);
        existingRoute.setStation(station);
        existingRoute.setArrivalTime(trainRouteDto.getArrivalTime());
        existingRoute.setStationOrder(trainRouteDto.getStationOrder());
        existingRoute.setHaltMinutes(trainRouteDto.getHaltMinutes());
        existingRoute.setDepartureTime(trainRouteDto.getDepartureTime());
        existingRoute.setDistanceFromSource(trainRouteDto.getDistanceFromSource());

        TrainRoute trainRoute = this.trainRouteRepository.save(existingRoute);

        return modelMapper.map(trainRoute, TrainRouteDto.class);
    }

    @Override
    public void deleteRoute(Long id) {
        TrainRoute existingRoute = this.trainRouteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Train Route not found with id: " + id));
        this.trainRouteRepository.delete(existingRoute);
    }
}
