package com.airobosoft.controller.admin;

import com.airobosoft.dto.StationDTO;
import com.airobosoft.service.admin.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<StationDTO> createStation(
            @RequestBody StationDTO stationDto) {

        StationDTO dto = stationService.createStation(stationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
