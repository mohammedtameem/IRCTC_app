package com.airobosoft.service;

import com.airobosoft.dto.TrainRouteDto;

import java.util.List;

public interface TrainRouteService {

    TrainRouteDto addRoute(TrainRouteDto trainRouteDto);

    List<TrainRouteDto> getRoutesByTrainId(Long trainId);

    TrainRouteDto updateRoute(TrainRouteDto trainRouteDto, Long id);

    void deleteRoute(Long id);
}
