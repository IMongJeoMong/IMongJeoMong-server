package com.imongjeomong.imongjeomongserver.opendata.tasu;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
public class TasuController {

    private final TasuService tasuService;

    @GetMapping("/tasu/save")
    public void saveTasu() throws UnsupportedEncodingException {
        tasuService.saveTasuInfo();
    }
}
