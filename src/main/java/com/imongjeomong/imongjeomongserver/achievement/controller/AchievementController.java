package com.imongjeomong.imongjeomongserver.achievement.controller;

import com.imongjeomong.imongjeomongserver.achievement.model.service.AchievementService;
import com.imongjeomong.imongjeomongserver.dto.AchievementDto;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;
    private final JwtUtil jwtUtil;

    @GetMapping("/achievement/mine/list")
    public DataResponse<List<AchievementDto>> getMyAchievementList(HttpServletRequest request) {
        String accessToken = "";
        Long memberId = null;
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
            memberId = jwtUtil.getMemberId(accessToken);
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        List<AchievementDto> myAchievementList = achievementService.getMyAchievementList(memberId);
        DataResponse<List<AchievementDto>> response = new DataResponse<>(200, "업적 리스트 조회 성공");
        response.setData(myAchievementList);
        return response;
    }
}
