package com.imongjeomong.imongjeomongserver.quest.controller;

import com.imongjeomong.imongjeomongserver.quest.model.service.QuestService;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questServiceImpl;

    @GetMapping("/daily")
    public DataResponse<?> getDailyQuestList(HttpServletRequest request){
        DataResponse<List> dataResponse = new DataResponse<>(200, "일일 퀘스트 목록입니다.");
        dataResponse.setData(questServiceImpl.getDailyQuestList(request));
        return dataResponse;
    }
}
