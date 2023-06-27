package com.imongjeomong.imongjeomongserver.opendata.parking;

import com.imongjeomong.imongjeomongserver.opendata.tasu.TasuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("/opendata/parking-lot/save")
    public void saveParkingLot() throws UnsupportedEncodingException {
        parkingLotService.saveParkingLotInfo();
    }

}
