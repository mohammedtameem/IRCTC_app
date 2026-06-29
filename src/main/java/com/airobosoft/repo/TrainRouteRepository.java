package com.airobosoft.repo;

import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {
    List<TrainRoute> findByTrain(Train train);
}
