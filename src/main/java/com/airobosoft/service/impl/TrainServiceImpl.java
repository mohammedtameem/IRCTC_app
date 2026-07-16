package com.airobosoft.service.impl;

import com.airobosoft.dto.AvailableTrainResponse;
import com.airobosoft.dto.TrainDTO;
import com.airobosoft.dto.UserTrainSearchRequest;
import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainRoute;
import com.airobosoft.entity.TrainSchedule;
import com.airobosoft.entity.TrainSeat;
import com.airobosoft.enums.CoachType;
import com.airobosoft.repo.TrainRepository;
import com.airobosoft.repo.TrainScheduleRepository;
import com.airobosoft.reports.ExcelGenerator;
import com.airobosoft.service.TrainService;
import com.airobosoft.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;


@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public TrainDTO add(TrainDTO train) {

        Train trainEntity = modelMapper.map(train, Train.class);
        return modelMapper.map(trainRepository.save(trainEntity), TrainDTO.class);
    }

    public List<TrainDTO> addTrainBulk(List<TrainDTO> trainDtos){

        List<Train> trains = trainDtos.stream()
                .map(dto ->
                        modelMapper.map(
                                dto,
                                Train.class
                        ))
                .toList();

        List<Train> savedTrains =
                trainRepository.saveAll(trains);

        return savedTrains.stream()
                .map(train ->
                        modelMapper.map(
                                train,
                                TrainDTO.class
                        ))
                .toList();
    }
    // SELECT *
    public Page<TrainDTO> all(int page,
                              int size,
                              String sortBy,
                              String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Train> trainPage =
                trainRepository.findAll(pageable);

        return trainPage.map(train ->
                modelMapper.map(train, TrainDTO.class));
    }

    public TrainDTO getTrain(Long id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new NoSuchElementException("with train id : " + id));
        return modelMapper.map(train, TrainDTO.class);
    }


    public ByteArrayInputStream exportTrainsToExcel() {

        List<TrainDTO> trains =
                trainRepository.findAll()
                        .stream()
                        .map(train ->
                                modelMapper.map(
                                        train,
                                        TrainDTO.class
                                ))
                        .toList();

        return ExcelGenerator.generateExcel(trains);
    }

    public void sendTrainReport(String email) {

        List<TrainDTO> trains =
                trainRepository.findAll()
                        .stream()
                        .map(train ->
                                modelMapper.map(
                                        train,
                                        TrainDTO.class
                                ))
                        .toList();

        System.out.println(trains.size());

        ByteArrayInputStream excelFile =
                ExcelGenerator.generateExcel(trains);

        emailService.sendExcelReport(
                email,
                excelFile
        );
    }

    @Override
    public List<AvailableTrainResponse> userSearchTrains(UserTrainSearchRequest searchRequest) {

        List<Train> matchedTrains =
                trainRepository.findTrainBySourceAndDestination(
                        searchRequest.getSourceStationId(),
                        searchRequest.getDestinationStationId());

        List<AvailableTrainResponse> responseList = new ArrayList<>();

        for (Train train : matchedTrains) {

            Integer sourceOrder = null;
            Integer destinationOrder = null;
            TrainRoute sourceRoute = null;

            // Loop routes only once
            for (TrainRoute route : train.getRoutes()) {

                if (route.getStation().getId().equals(searchRequest.getSourceStationId())) {
                    sourceOrder = route.getStationOrder();
                    sourceRoute = route;
                }

                if (route.getStation().getId().equals(searchRequest.getDestinationStationId())) {
                    destinationOrder = route.getStationOrder();
                }
            }

            if (sourceOrder == null ||
                    destinationOrder == null ||
                    sourceOrder >= destinationOrder) {
                continue;
            }

            // Find today's schedule only once
            TrainSchedule trainSchedule = train.getSchedules()
                    .stream()
                    .filter(s -> s.getRunningDate().equals(searchRequest.getJourneyDate()))
                    .findFirst()
                    .orElse(null);

            if (trainSchedule == null) {
                continue;
            }

            Map<CoachType, Integer> seatMap = new HashMap<>();
            Map<CoachType, Double> priceMap = new HashMap<>();

            for (TrainSeat seat : trainSchedule.getTrainSeats()) {
                seatMap.merge(seat.getCoachType(),
                        seat.getAvailableSeats(),
                        Integer::sum);

                priceMap.putIfAbsent(seat.getCoachType(),
                        seat.getPrice());
            }

            responseList.add(
                    AvailableTrainResponse.builder()
                            .trainId(train.getId())
                            .trainNumber(train.getTrainNo())
                            .trainName(train.getName())
                            .scheduleDate(trainSchedule.getRunningDate())
                            .arrivalTime(sourceRoute.getArrivalTime())
                            .departureTime(sourceRoute.getDepartureTime())
                            .seatsAvailable(seatMap)
                            .priceByCoach(priceMap)
                            .build()
            );
        }

        return responseList;
    }

//    @Override
//    public List<AvailableTrainResponse> userSearchTrains(UserTrainSearchRequest searchRequest) {
//
//        List<TrainRoute> matchedTrains = this.trainRepository.findTrainBySourceAndDestinationInOrder(searchRequest.getSourceStationId(), searchRequest.getDestinationStationId());
//
//        List<AvailableTrainResponse> list = matchedTrains.stream().map(trainRoute -> {
//            TrainSchedule trainSchedule = trainScheduleRepository.findByTrainIdAndRunningDate(trainRoute.getTrain().getId(), searchRequest.getJourneyDate()).orElse(null);
//            if(trainSchedule==null){
//                return null;
//            }
//            Map<CoachType, Integer> seatMap = new HashMap<>();
//            Map<CoachType, Double> priceMap = new HashMap<>();
//
//            for(TrainSeat trainSeat:trainSchedule.getTrainSeats()){
//                seatMap.merge(trainSeat.getCoachType(), trainSeat.getAvailableSeats(), Integer::sum);
//                priceMap.putIfAbsent(trainSeat.getCoachType(), trainSeat.getPrice());
//            }
//
//            return AvailableTrainResponse.builder()
//                    .trainId(trainRoute.getTrain().getId())
//                    .trainNumber(trainRoute.getTrain().getTrainNo())
//                    .trainName(trainRoute.getTrain().getName())
//                    .departureTime(trainRoute.getDepartureTime())
//                    .arrivalTime(trainRoute.getArrivalTime())
//                    .seatsAvailable(seatMap)
//                    .priceByCoach(priceMap)
//                    .build();
//        }).filter(Objects::nonNull).toList();
//        return list;
//    }

//    public List<AvailableTrainResponse> searchTrains(UserTrainSearchRequest searchRequest){
//    }
}
