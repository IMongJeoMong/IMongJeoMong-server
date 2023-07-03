package com.imongjeomong.imongjeomongserver.achievement.model.service;

import com.imongjeomong.imongjeomongserver.achievement.model.repository.AchievementRepository;
import com.imongjeomong.imongjeomongserver.achievement.model.repository.MyAchievementRepository;
import com.imongjeomong.imongjeomongserver.attraction.model.repository.MyAttractionRepository;
import com.imongjeomong.imongjeomongserver.dto.AchievementDto;
import com.imongjeomong.imongjeomongserver.item.model.repository.ItemRepository;
import com.imongjeomong.imongjeomongserver.item.model.repository.MyItemRepository;
import com.imongjeomong.imongjeomongserver.review.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final MyAchievementRepository myAchievementRepository;

    private final MyAttractionRepository myAttractionRepository;
    private final ReviewRepository reviewRepository;
    private final MyItemRepository myItemRepository;

    @Override
    public List<AchievementDto> getMyAchievementList(Long memberId) {
        /*
        반환해야 할 정보 : 업적, 업적별 카운트, 경험치, 이름, 보상 받았는지 여부 (금, 은, 동 인지)
            동 - 1, 은 - 3, 금 - 5

        업적 정보
            1. 출석하기
            2. 방문한 관광지 개수
            3. 리뷰 작성하기
            4. 아이템 구매 횟수
         */
        // 1. 출석하기

        // 2. 방문한 관광지 개수
        Long attractionCount = myAttractionRepository.countByMemberId(memberId);

        // 3. 리뷰 작성 개수
        Long reviewCount = reviewRepository.countByMemberId(memberId);

        // 4. 아이템 구매 횟수
        Long itemCount = myItemRepository.countByMemberId(memberId);

        log.info("관광지={}, 리뷰={}, 아이템={}", attractionCount, reviewCount, itemCount);

        return null;
    }
}
