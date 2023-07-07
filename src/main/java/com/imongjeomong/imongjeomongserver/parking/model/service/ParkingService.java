package com.imongjeomong.imongjeomongserver.parking.model.service;

import com.imongjeomong.imongjeomongserver.dto.ParkingDto;

import java.util.List;

public interface ParkingService {
    List<ParkingDto> getParkingList();
}
