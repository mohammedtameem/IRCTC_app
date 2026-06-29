package com.airobosoft.controller.admin;

import com.airobosoft.dto.ErrorResponse;
import com.airobosoft.dto.TrainDTO;
import com.airobosoft.dto.TrainImageDataWithResource;
import com.airobosoft.entity.Train;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.service.TrainImageService;
import com.airobosoft.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final TrainRepository trainRepository;
    private final TrainImageService trainImageService;

    @GetMapping()
    @Operation(summary = "Get all Trains", description = "Returns paginated list of trains")
    public ResponseEntity<Page<TrainDTO>> getAllTrains(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "trainNo")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ){
        return ResponseEntity.ok(trainService.all(page, size, sortBy, direction));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add more than one train record at a time")
    public ResponseEntity<List<TrainDTO>> addMultipleTrains(@RequestBody List<@Valid TrainDTO> train){
        return new ResponseEntity<>(trainService.addTrainBulk(train), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO) {

        System.out.println(trainDTO.getTrainNo());
        return new ResponseEntity<>(trainDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainDTO> getTrainByID( @Parameter(description = "Train ID", example = "1") @PathVariable  Long id){
        return ResponseEntity.ok(trainService.getTrain(id));
    }

    @DeleteMapping
    public void deleteTrain(Long id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Train not found with train id : "+ id));
        trainRepository.delete(train);
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportExcel() {

        ByteArrayInputStream excelFile =
                trainService.exportTrainsToExcel();

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

        trainService.sendTrainReport(email);

        return ResponseEntity.ok(
                "Excel report sent successfully"
        );
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadTrainImage(

            @PathVariable Long id,

            @RequestParam("image")
            MultipartFile image

    ) throws Exception {

        String contentType = image.getContentType();
        if (contentType != null &&
                contentType.toLowerCase().startsWith("image")) {
            return new ResponseEntity<>(
                    trainImageService.upload(image, id),
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
                trainImageService.getImage(id);

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                trainImage.fileType()))
                .body(
                        trainImage.resource()
                );
    }
}
