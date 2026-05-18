package com.airobosoft.service;

import com.airobosoft.dto.TrainImageDataWithResource;
import com.airobosoft.dto.TrainImageResponse;
import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainImage;
import com.airobosoft.exception.ResourceNotFoundException;
import com.airobosoft.repo.TrainImageRepository;
import com.airobosoft.repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TrainImageService {

    @Autowired
    private TrainImageRepository trainImageRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StorageService storageService;


    public TrainImageResponse upload(
            MultipartFile file,
            Long id
    ) throws Exception {

        // Find train

        Train train =
                trainRepository.findById(id)

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Train not found !!"
                                )
                        );


        String generatedFileName =
                storageService.uploadFile(file);


        TrainImage trainImage =
                new TrainImage();

        trainImage.setFileName(
                generatedFileName
        );

        trainImage.setFileType(
                file.getContentType()
        );

        trainImage.setSize(
                file.getSize()
        );

        trainImage.setTrain(train);

        train.setTrainImage(trainImage);


        Train savedTrain =
                trainRepository.save(train);

        return TrainImageResponse.from(

                savedTrain.getTrainImage(),

                "http://localhost:8085",

                id
        );
    }

    // FETCH IMAGE

    public TrainImageDataWithResource getImage(
            Long imageId
    ) throws Exception {

        TrainImage image =

                trainImageRepository.findById(imageId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Image Not Found"
                                )
                        );

        Resource resource =

                storageService.downloadFile(
                        image.getFileName()
                );

        return new TrainImageDataWithResource(

                image.getFileType(),

                resource
        );
    }
}