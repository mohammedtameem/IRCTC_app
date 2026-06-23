package com.airobosoft.service.admin;

import com.airobosoft.dto.StationDTO;
import com.airobosoft.entity.Station;
import com.airobosoft.repo.admin.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<StationDTO> listStations(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Station> stations = stationRepository.findAll(pageable);

        return stations.map(this::convertToDTO);
    }

    private StationDTO convertToDTO(Station station) {
        return StationDTO.builder()
                .id(station.getId())
                .code(station.getCode())
                .name(station.getName())
                .city(station.getCity())
                .state(station.getState())
                .build();
    }
}