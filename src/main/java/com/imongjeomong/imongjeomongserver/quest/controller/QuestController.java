package com.imongjeomong.imongjeomongserver.quest.controller;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.quest.model.service.QuestService;
import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questServiceImpl;

    /**
     * 일일 퀘스트 목록 조회
     */
    @GetMapping("/daily")
    public DataResponse<?> getDailyQuestList(HttpServletRequest request) {
        DataResponse<List> dataResponse = new DataResponse<>(200, "일일 퀘스트 목록입니다.");
        dataResponse.setData(questServiceImpl.getDailyQuestList(request));
        return dataResponse;
    }

    /**
     * 퀘스트 보상 받기
     */
    @PostMapping("/{myQuestId}")
    public CommonResponse getQuestReward(HttpServletRequest request, @PathVariable Long myQuestId) {
        CommonResponse response = new CommonResponse(200, "보상을 획득하였습니다.");
        questServiceImpl.getQuestReward(request, myQuestId);
        return response;
    }
}
