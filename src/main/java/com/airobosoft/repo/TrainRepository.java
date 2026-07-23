package com.airobosoft.repo;

import com.airobosoft.entity.Train;
import com.airobosoft.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNo(String trainNo);


@Query("""
        SELECT tr from TrainRoute tr
        WHERE tr.station.id = :sourceStationId OR tr.station.id = :destinationStationId
        GROUP BY tr.train.id
        HAVING SUM(CASE WHEN tr.station.id = :sourceStationId THEN 1 ELSE 0 END)>0
        AND SUM(CASE WHEN tr.station.id = :destinationStationId THEN 1 ELSE 0 END)>0
        AND (MIN(CASE WHEN tr.station.id = :sourceStationId THEN tr.stationOrder ELSE 999999 END) < MIN(CASE WHEN tr.station.id = :destinationStationId THEN tr.stationOrder ELSE 999999 END))
""")
    List<TrainRoute> findTrainBySourceAndDestinationInOrder(@Param("sourceStationId") Long sourceStationId, @Param("destinationStationId") Long destinationStationId);

    @Query("SELECT tr.train FROM TrainRoute tr WHERE tr.station.id = :sourceStationId OR tr.station.id = :destinationStationId")
    List<Train> findTrainBySourceAndDestination(@Param("sourceStationId") Long sourceStationId, @Param("destinationStationId") Long destinationStationId);
    Optional<Train> findByName(String name);
}