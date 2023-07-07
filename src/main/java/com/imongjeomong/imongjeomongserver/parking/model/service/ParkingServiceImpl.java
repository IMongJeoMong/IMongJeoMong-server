package com.imongjeomong.imongjeomongserver.parking.model.service;

import com.imongjeomong.imongjeomongserver.dto.ParkingDto;
import com.imongjeomong.imongjeomongserver.entity.ParkingLot;
import com.imongjeomong.imongjeomongserver.parking.model.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    @Override
    public List<ParkingDto> getParkingList() {
        List<ParkingLot> parkingList = parkingRepository.findAll();

        List<ParkingDto> parkingDtoList = new ArrayList<>();

        for (ParkingLot parkingLot : parkingList) {
            parkingDtoList.add(ParkingDto.toParkingDto(parkingLot));
        }

        return parkingDtoList;
    }
}
