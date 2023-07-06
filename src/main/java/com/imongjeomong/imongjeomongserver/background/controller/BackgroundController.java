package com.imongjeomong.imongjeomongserver.background.controller;

import com.imongjeomong.imongjeomongserver.background.model.service.BackgroundService;
import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/background")
@RequiredArgsConstructor
public class BackgroundController {

    private final BackgroundService backgroundServiceImpl;

    @GetMapping("/list")
    public DataResponse<?> getBackgroundList(){
        DataResponse<List<BackgroundDto>> dataResponse = new DataResponse<>(200, "배경이 조회되었습니다.");
        dataResponse.setData(backgroundServiceImpl.getBackgroundList());
        return dataResponse;
    }
}
