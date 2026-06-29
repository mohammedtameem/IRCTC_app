package com.airobosoft.controller.admin;

import com.airobosoft.dto.TrainRouteDto;
import com.airobosoft.service.TrainRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/train-routes")
public class TrainRouteController {
    private final TrainRouteService trainRouteService;

    @PostMapping
    public ResponseEntity<TrainRouteDto> addTrainRoute(@RequestBody TrainRouteDto trainRouteDto) {
        TrainRouteDto addedTrainRoute = this.trainRouteService.addRoute(trainRouteDto);
        return new ResponseEntity<>(addedTrainRoute, HttpStatus.CREATED);
    }

    @GetMapping("train/{trainId}")
    public ResponseEntity<List<TrainRouteDto>> getRoutesByTrainId(@PathVariable Long trainId) {
        List<TrainRouteDto> routes = this.trainRouteService.getRoutesByTrainId(trainId);
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TrainRouteDto>  updateTrainRoute(@PathVariable Long id, @RequestBody TrainRouteDto trainRouteDto) {
        TrainRouteDto updatedRoute = this.trainRouteService.updateRoute(trainRouteDto, id);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTrainRoute(@PathVariable Long id) {
        this.trainRouteService.deleteRoute(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
