package com.imongjeomong.imongjeomongserver.parking.controller;

import com.imongjeomong.imongjeomongserver.dto.ParkingDto;
import com.imongjeomong.imongjeomongserver.parking.model.service.ParkingService;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/parking/list")
    public DataResponse<List<ParkingDto>> getParkingList() {
        List<ParkingDto> parkingDtoList = parkingService.getParkingList();

        DataResponse<List<ParkingDto>> response = new DataResponse<>(200, "주차장 정보 리스트 조회 성공");
        response.setData(parkingDtoList);
        return response;
    }
}
