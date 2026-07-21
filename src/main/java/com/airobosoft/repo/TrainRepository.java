package com.airobosoft.repo;

import com.airobosoft.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    Optional<Train> findByName(String name);
}