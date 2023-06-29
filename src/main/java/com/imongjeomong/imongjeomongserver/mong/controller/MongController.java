package com.imongjeomong.imongjeomongserver.mong.controller;

import com.imongjeomong.imongjeomongserver.entity.Mong;
import com.imongjeomong.imongjeomongserver.mong.model.service.MongService;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mong")
@RequiredArgsConstructor
public class MongController {

    private final MongService mongServiceImpl;

    /* 전체 몽, 로그인 멤버 보유 몽 조회 */
    @GetMapping("/list")
    public DataResponse<?> getMongList(HttpServletRequest request){
        DataResponse<Map> dataResponse = new DataResponse<>(201, "몽/보유 몽 정보를 조회합니다.");
        dataResponse.setData(mongServiceImpl.getMongList(request));

        return dataResponse;
    }

    /* 몽 정보 조회 */
    @GetMapping("/{mongId}")
    public DataResponse<?> getMongInfo(@PathVariable Long mongId){
        DataResponse<Mong> dataResponse = new DataResponse<>(200, "[ " + mongId + " ]" + "몽 정보를 조회합니다.");
        dataResponse.setData(mongServiceImpl.getMongById(mongId));
        return dataResponse;
    }
}
