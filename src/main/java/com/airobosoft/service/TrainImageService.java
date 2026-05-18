package com.airobosoft.service;

import com.airobosoft.dto.TrainImageDataWithResource;
import com.airobosoft.dto.TrainImageResponse;
import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainImage;
import com.airobosoft.exception.ResourceNotFoundException;
import com.airobosoft.repo.TrainImageRepository;
import com.airobosoft.repo.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class TrainImageService {

    @Value("${train.image.folder.path}")
    private String folderPath;

    @Autowired
    private TrainImageRepository trainImageRepository;

    @Autowired
    private TrainRepository trainRepository;

    public TrainImageResponse upload(MultipartFile file, Long id)  throws IOException {

        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("train not found !!"));

        if(!Files.exists(Path.of(folderPath))) {
                Files.createDirectories(Paths.get(folderPath));
        }

        String filePath = folderPath + UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(),Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        TrainImage trainImage = new TrainImage();
        trainImage.setFileName(filePath);
        trainImage.setFileType(file.getContentType());
        trainImage.setSize(file.getSize());

        trainImage.setTrain(train);
        train.setTrainImage(trainImage);

        Train savedTrain = trainRepository.save(train);
        return TrainImageResponse.from(savedTrain.getTrainImage(),"https://localhost:8085",id);


     }

    public TrainImageDataWithResource getImage(
            Long imageId
    ) throws MalformedURLException {

        TrainImage image =
                trainImageRepository.findById(imageId)

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Image Not Found"
                                )
                        );

        Path imagePath = Paths.get(
                folderPath,
                image.getFileName()
        );

        Resource resource =
                new UrlResource(
                        imagePath.toUri()
                );

        return new TrainImageDataWithResource(
                image.getFileType(),
                resource
        );
    }
}
