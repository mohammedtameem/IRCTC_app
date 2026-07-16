package com.airobosoft.repo;

import com.airobosoft.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    List<TrainSchedule> findByTrainId(Long trainId);
    Optional<TrainSchedule> findByTrainIdAndRunningDate(Long trainId, LocalDate runningDate);
}
