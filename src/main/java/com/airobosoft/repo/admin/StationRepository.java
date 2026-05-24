package com.airobosoft.repo.admin;

import com.airobosoft.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    boolean existsByCode(String code);

}