package com.airobosoft.repo;

import com.airobosoft.entity.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainSeatRepository extends JpaRepository<TrainSeat, Long> {
    List<TrainSeat> findByTrainScheduleId(Long trainScheduleId);
}
