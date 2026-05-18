package com.airobosoft.controller;

import com.airobosoft.dto.ErrorResponse;
import com.airobosoft.dto.TrainDTO;
import com.airobosoft.dto.TrainImageDataWithResource;
import com.airobosoft.service.TrainImageService;
import com.airobosoft.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/trains")
public class TrainController {

    @Autowired
    private TrainService service;

    private TrainImageService imageService;

    @Operation(
            summary = "Get All Trains",
            description = "Returns paginated train list"
    )    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Page<TrainDTO>> listTrain(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        return ResponseEntity.ok(
                service.all(page, size, sortBy, direction)
        );
    }

    @PostMapping
    public ResponseEntity<TrainDTO> addTrain(
            @Valid @RequestBody TrainDTO train) {

        TrainDTO savedTrain = service.add(train);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTrain);
    }

    @GetMapping("/{trainNo}")
    public ResponseEntity<TrainDTO> getTrainById(
            @PathVariable Long id) {

        TrainDTO train = service.get(id);

        return ResponseEntity.ok(train);
    }

    @DeleteMapping("/{trainNo}")
    public ResponseEntity<Void> deleteByTrainId(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportExcel() {

        ByteArrayInputStream excelFile =
                service.exportTrainsToExcel();

        HttpHeaders headers = new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "attachment; filename=trains.xlsx"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(excelFile));
    }

    @PostMapping("/send-report")
    public ResponseEntity<String> sendReport(

            @RequestParam String email
    ) {

        service.sendTrainReport(email);

        return ResponseEntity.ok(
                "Excel report sent successfully"
        );
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadTrainImage(

            @PathVariable Long id,

            @RequestParam("image")
            MultipartFile image

    ) throws IOException {

        String contentType = image.getContentType();

        if (contentType != null &&
                contentType.toLowerCase().startsWith("image")) {

            return new ResponseEntity<>(
                    imageService.upload(image, id),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(

                new ErrorResponse(
                        "Image not uploaded",
                        "INVALID_FILE",
                        false,
                        LocalDateTime.now()
                ),

                HttpStatus.BAD_REQUEST
        );
    }


    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(
            @PathVariable Long id
    ) throws Exception {

        TrainImageDataWithResource trainImage =
                imageService.getImage(id);

        return ResponseEntity.ok()
                  .contentType(
                        MediaType.parseMediaType(
                                trainImage.fileType()))
                .body(
                        trainImage.resource()
                );
    }


}
