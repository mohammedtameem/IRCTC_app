package com.airobosoft.service.admin;

import com.airobosoft.dto.StationDTO;
import org.springframework.data.domain.Page;

public interface StationService {

    StationDTO createStation(StationDTO stationDTO);

    Page<StationDTO> listStations(int page, int size, String sortBy, String sortDir);
}