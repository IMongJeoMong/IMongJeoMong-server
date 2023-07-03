package com.imongjeomong.imongjeomongserver.quest.model.service;

import com.imongjeomong.imongjeomongserver.dto.MyQuestDTO;
import com.imongjeomong.imongjeomongserver.entity.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QuestService {
    List<MyQuestDTO> getDailyQuestList(HttpServletRequest request);

    Member getQuestReward(HttpServletRequest request, Long myQuestId);
}
