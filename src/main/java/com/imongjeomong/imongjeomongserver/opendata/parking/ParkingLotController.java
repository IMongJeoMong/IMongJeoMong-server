package com.imongjeomong.imongjeomongserver.opendata.parking;

import com.imongjeomong.imongjeomongserver.opendata.tasu.TasuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class ParkingLotController {

    @GetMapping("/opendata/parking/save")
    public void saveParkingLot() throws UnsupportedEncodingException {

    }

}
