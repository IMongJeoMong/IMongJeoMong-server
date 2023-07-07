package com.imongjeomong.imongjeomongserver.parking.model.repository;

import com.imongjeomong.imongjeomongserver.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingLot, Long> {
}
