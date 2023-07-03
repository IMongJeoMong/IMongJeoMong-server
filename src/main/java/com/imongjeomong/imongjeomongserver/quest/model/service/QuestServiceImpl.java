package com.imongjeomong.imongjeomongserver.quest.model.service;

import com.imongjeomong.imongjeomongserver.dto.MyQuestDTO;
import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.MyQuest;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import com.imongjeomong.imongjeomongserver.quest.model.repository.MyQuestRepository;
import com.imongjeomong.imongjeomongserver.quest.model.repository.QuestRepositoy;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {

    private final QuestRepositoy questRepositoy;
    private final MyQuestRepository myQuestRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<MyQuestDTO> getDailyQuestList(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        Long memberId = jwtUtil.getMemberId(accessToken);
        List<MyQuest> dailyQuestList = myQuestRepository.findAllByMemberId(memberId);
        List<MyQuestDTO> myQuestDTOList = new ArrayList<>();
        dailyQuestList.stream().forEach(value -> {
            boolean clearFlag = false;
            if (value.getClearTime() != null) {
                clearFlag = Duration.between(value.getClearTime(), LocalDateTime.now()).toHours() <= 24;
            }

            boolean rewardFlag = false;
            if (value.getRewardTime() != null) {
                rewardFlag = Duration.between(value.getRewardTime(), LocalDateTime.now()).toHours() <= 24;
            }

            MyQuestDTO myQuestDTO = MyQuestDTO.builder()
                    .id(value.getId())
                    .name(value.getQuest().getName())
                    .exp(value.getQuest().getExp())
                    .gold(value.getQuest().getGold())
                    .clearFlag(clearFlag)
                    .rewardFlag(rewardFlag).build();
            myQuestDTOList.add(myQuestDTO);
        });

        return myQuestDTOList;

    }

    @Transactional
    @Override
    public Member getQuestReward(HttpServletRequest request, Long myQuestId) {
        String accessToken = getAccessToken(request);
        Optional<Member> findMember = memberRepository.findById(jwtUtil.getMemberId(accessToken));
        MyQuest findMyQuest = myQuestRepository.findById(myQuestId).get();
        Member modifyMember = null;
        if (findMember.isPresent()){
            modifyMember = findMember.get();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("gold", modifyMember.getGold() + findMyQuest.getQuest().getGold());
            paramMap.put("updateTime", LocalDateTime.now());
            modifyMember.modifyValue(paramMap);
            modifyMember = memberRepository.save(modifyMember);

            findMyQuest.setRewardTime(LocalDateTime.now());
            myQuestRepository.save(findMyQuest);
        }
        return modifyMember;
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
