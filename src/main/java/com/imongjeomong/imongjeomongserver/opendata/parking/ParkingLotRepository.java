package com.imongjeomong.imongjeomongserver.opendata.parking;

import com.imongjeomong.imongjeomongserver.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
