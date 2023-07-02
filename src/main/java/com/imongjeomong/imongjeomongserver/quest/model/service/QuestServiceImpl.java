package com.imongjeomong.imongjeomongserver.quest.model.service;

import com.imongjeomong.imongjeomongserver.entity.MyQuest;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.quest.model.repository.MyQuestRepository;
import com.imongjeomong.imongjeomongserver.quest.model.repository.QuestRepositoy;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService{

    private final QuestRepositoy questRepositoy;
    private final MyQuestRepository myQuestRepository;
    private final JwtUtil jwtUtil;


    @Override
    public void getDailyQuestList(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        Long memberId = jwtUtil.getMemberId(accessToken);
        List<MyQuest> daliyQuestList = myQuestRepository.findAllByMemberId(memberId);

    }

    /* 헤더 Authorization 파싱 */
    private static String getAccessToken(HttpServletRequest request) {
        String accessToken = "";
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }
        return accessToken;
    }
}
