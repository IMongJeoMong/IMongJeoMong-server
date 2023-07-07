package com.imongjeomong.imongjeomongserver.opendata.tasu;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
public class TasuOpenDataController {

    private final TasuOpenDataService tasuOpenDataService;

    @GetMapping("opendata/tasu/save")
    public void saveTasu() throws UnsupportedEncodingException {
        tasuOpenDataService.saveTasuInfo();
    }
}
