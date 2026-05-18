package com.airobosoft.repo;

import com.airobosoft.entity.TrainImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainImageRepository extends JpaRepository<TrainImage,Long> {
}
