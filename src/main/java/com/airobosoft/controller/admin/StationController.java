package com.airobosoft.controller.admin;

import com.airobosoft.config.AppConstants;
import com.airobosoft.dto.StationDTO;
import com.airobosoft.service.admin.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<StationDTO> createStation(
            @Valid  @RequestBody StationDTO stationDto) {

        StationDTO dto = stationService.createStation(stationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<StationDTO>> listStations(
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value="size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value ="sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc") String sortDir
    ) {

        Page<StationDTO> stationDto = stationService.listStations(
                page,
                size,
                sortBy,
                sortDir
        );

        return new ResponseEntity<>(stationDto,HttpStatus.OK);

    }
}
