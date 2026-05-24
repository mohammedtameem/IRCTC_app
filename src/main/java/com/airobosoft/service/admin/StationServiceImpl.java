package com.airobosoft.service.admin;

import com.airobosoft.dto.StationDTO;
import com.airobosoft.entity.Station;
import com.airobosoft.repo.admin.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public StationDTO createStation(StationDTO stationDTO) {

        if(stationRepository.existsByCode(stationDTO.getCode())) {
            throw new RuntimeException("Station code already exists");
        }

        Station station = Station.builder()
                .code(stationDTO.getCode())
                .name(stationDTO.getName())
                .city(stationDTO.getCity())
                .state(stationDTO.getState())
                .build();

        Station savedStation = stationRepository.save(station);

        return StationDTO.builder()
                .id(savedStation.getId())
                .code(savedStation.getCode())
                .name(savedStation.getName())
                .city(savedStation.getCity())
                .state(savedStation.getState())
                .build();
    }
}