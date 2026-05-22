//package com.airobosoft.service;
//
//import java.util.List;
//
//import com.airobosoft.dto.TrainDTO;
//import com.airobosoft.entity.Train;
//import com.airobosoft.repo.TrainRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.*;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TrainServiceTest {
//
//    @Mock
//    private TrainRepository trainRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private TrainService trainService;
//
//    // =========================
//    // ADD TRAIN - POSITIVE
//    // =========================
//
//    @Test
//    void shouldAddTrainSuccessfully() {
//
//        // GIVEN
//
//        TrainDTO dto = new TrainDTO(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        Train entity = new Train(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        when(modelMapper.map(dto, Train.class))
//                .thenReturn(entity);
//
//        when(trainRepository.save(entity))
//                .thenReturn(entity);
//
//        when(modelMapper.map(entity, TrainDTO.class))
//                .thenReturn(dto);
//
//        // WHEN
//
//        TrainDTO result = trainService.add(dto);
//
//        // THEN
//
//        assertNotNull(result);
//
//        assertEquals("12627", result.getTrainNo());
//
//        verify(trainRepository, times(1))
//                .save(entity);
//    }
//
//    // =========================
//    // ADD TRAIN - NEGATIVE
//    // =========================
//
//    @Test
//    void shouldThrowExceptionWhileSavingTrain() {
//
//        // GIVEN
//
//        TrainDTO dto = new TrainDTO(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        Train entity = new Train(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        when(modelMapper.map(dto, Train.class))
//                .thenReturn(entity);
//
//        when(trainRepository.save(entity))
//                .thenThrow(new RuntimeException("DB Error"));
//
//        // WHEN + THEN
//
//        RuntimeException exception =
//                assertThrows(RuntimeException.class,
//                        () -> trainService.add(dto));
//
//        assertEquals("DB Error",
//                exception.getMessage());
//    }
//
//    // =========================
//    // GET TRAIN - POSITIVE
//    // =========================
//
//    @Test
//    void shouldReturnTrainById() {
//
//        // GIVEN
//
//        Train entity = new Train(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        TrainDTO dto = new TrainDTO(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        when(trainRepository.findById(1L))
//                .thenReturn(Optional.of(entity));
//
//        when(modelMapper.map(entity, TrainDTO.class))
//                .thenReturn(dto);
//
//        // WHEN
//
//        TrainDTO result = trainService.get(1L);
//
//        // THEN
//
//        assertNotNull(result);
//
//        assertEquals("12627", result.getTrainNo());
//
//        verify(trainRepository)
//                .findById(1L);
//    }
//
//    // =========================
//    // GET TRAIN - NEGATIVE
//    // =========================
//
//    @Test
//    void shouldThrowExceptionWhenTrainNotFound() {
//
//        // GIVEN
//
//        when(trainRepository.findById(1L))
//                .thenReturn(Optional.empty());
//
//        // WHEN + THEN
//
//        RuntimeException exception =
//                assertThrows(RuntimeException.class,
//                        () -> trainService.get(1L));
//
//        assertEquals("Train Not Found",
//                exception.getMessage());
//    }
//
//    // =========================
//    // GET ALL TRAINS - POSITIVE
//    // =========================
//
//    @Test
//    void shouldReturnAllTrainsWithPagination() {
//
//        // GIVEN
//
//        Train train = new Train(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        TrainDTO dto = new TrainDTO(
//                1L,
//                "12627",
//                "Karnataka Express",
//                20
//        );
//
//        Pageable pageable =
//                PageRequest.of(
//                        0,
//                        5,
//                        Sort.by("id").ascending()
//                );
//
//        Page<Train> trainPage =
//                new PageImpl<>(List.of(train));
//
//        when(trainRepository.findAll(pageable))
//                .thenReturn(trainPage);
//
//        when(modelMapper.map(train, TrainDTO.class))
//                .thenReturn(dto);
//
//        // WHEN
//
//        Page<TrainDTO> result =
//                trainService.all(
//                        0,
//                        5,
//                        "id",
//                        "asc"
//                );
//
//        // THEN
//
//        assertEquals(1,
//                result.getContent().size());
//
//        assertEquals("12627",
//                result.getContent()
//                        .get(0)
//                        .getTrainNo());
//
//        verify(trainRepository)
//                .findAll(pageable);
//    }
//
//    // =========================
//    // DELETE TRAIN - POSITIVE
//    // =========================
//
//    @Test
//    void shouldDeleteTrainSuccessfully() {
//
//        // WHEN
//
//        trainService.delete(1L);
//
//        // THEN
//
//        verify(trainRepository, times(1))
//                .deleteById(1L);
//    }
//
//    // =========================
//    // DELETE TRAIN - NEGATIVE
//    // =========================
//
//    @Test
//    void shouldThrowExceptionWhileDeletingTrain() {
//
//        doThrow(new RuntimeException("Delete Failed"))
//                .when(trainRepository)
//                .deleteById(1L);
//
//        RuntimeException exception =
//                assertThrows(RuntimeException.class,
//                        () -> trainService.delete(1L));
//
//        assertEquals("Delete Failed",
//                exception.getMessage());
//    }
//}