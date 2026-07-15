package com.airobosoft.repo;

import com.airobosoft.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {
    List<TrainSchedule> findByTrainId(Long trainId);
}
