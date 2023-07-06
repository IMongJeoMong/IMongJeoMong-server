package com.imongjeomong.imongjeomongserver.background.controller;

import com.imongjeomong.imongjeomongserver.background.model.service.BackgroundService;
import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/background")
@RequiredArgsConstructor
public class BackgroundController {

    private final BackgroundService backgroundServiceImpl;

    /* 전체 배경 리스트 조회 */
    @GetMapping("/list")
    public DataResponse<?> getBackgroundList() {
        DataResponse<List<BackgroundDto>> dataResponse = new DataResponse<>(200, "배경이 조회되었습니다.");
        dataResponse.setData(backgroundServiceImpl.getBackgroundList());
        return dataResponse;
    }

    /* 보유한 배경 리스트 조회 */
    @GetMapping("/own/list")
    public DataResponse<?> getMyBackgroundList(HttpServletRequest request) {
        DataResponse<List<MyBackgroundDto>> dataResponse = new DataResponse<>(200, "보유한 배경이 조회되었습니다.");
        dataResponse.setData(backgroundServiceImpl.getMyBackgroundList(request));
        return dataResponse;
    }

    /* Id를 통해 배경 조회 */
    @GetMapping("/{backgroundId}")
    public DataResponse<?> getBackgroundById(@PathVariable Long backgroundId) {
        DataResponse<BackgroundDto> dataResponse = new DataResponse<>(200, "배경이 조회되었습니다.");
        dataResponse.setData(backgroundServiceImpl.getBackgroundById(backgroundId));
        return dataResponse;
    }

    /* MyBackgroundId를 통해 보유한 배경 조회 */
    @GetMapping("/own/{myBackgroundId}")
    public DataResponse<?> getMyBackgroundById(@PathVariable Long myBackgroundId, HttpServletRequest request) {
        DataResponse<MyBackgroundDto> dataResponse = new DataResponse<>(200, "보유한 배경이 조회되었습니다");
        dataResponse.setData(backgroundServiceImpl.getMyBackgroundById(request, myBackgroundId));
        return dataResponse;
    }
}
