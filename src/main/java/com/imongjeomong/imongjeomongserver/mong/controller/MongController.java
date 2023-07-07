package com.imongjeomong.imongjeomongserver.mong.controller;

import com.imongjeomong.imongjeomongserver.dto.MyMongDto;
import com.imongjeomong.imongjeomongserver.entity.Mong;
import com.imongjeomong.imongjeomongserver.mong.model.service.MongService;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mong")
@RequiredArgsConstructor
public class MongController {

    private final MongService mongServiceImpl;
    private final JwtUtil jwtUtil;

    /* 전체 몽, 로그인 멤버 보유 몽 조회 */
    @GetMapping("/list")
    public DataResponse<?> getMongList(HttpServletRequest request) {
        DataResponse<List> dataResponse = new DataResponse<>(201, "전체 몽 리스트를 조회합니다.");
        dataResponse.setData(mongServiceImpl.getMongList(request));
        return dataResponse;
    }

    @GetMapping("/own/list")
    public DataResponse<?> getMyMongList(HttpServletRequest request) {
        DataResponse<List<MyMongDto>> dataResponse =new DataResponse<>(201, "보유 몽 리스트를 조회합니다.");
        dataResponse.setData(mongServiceImpl.getMyMongList(jwtUtil.getMemberId(jwtUtil.getAccessToken(request))));
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
