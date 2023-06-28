package com.imongjeomong.imongjeomongserver.opendata.attraction;

import com.imongjeomong.imongjeomongserver.opendata.parking.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class AttractionOpenDataController {

    private final AttractionOpenDataService attractionOpenDataService;

    @GetMapping("/opendata/attraction/save")
    public void saveAttraction() throws UnsupportedEncodingException {
        attractionOpenDataService.saveAttractionOpenData();
    }

}
