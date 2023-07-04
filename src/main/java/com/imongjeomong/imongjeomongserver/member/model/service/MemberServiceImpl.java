package com.imongjeomong.imongjeomongserver.member.model.service;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.item.model.repository.MyItemRepository;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import com.imongjeomong.imongjeomongserver.mong.model.repository.MyMongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MyMongRepository myMongRepository;

    private final MyItemRepository myItemRepository;
//    private final MyBackgroundRepository myBackgroundRepository;

    @Transactional
    @Override
    public void signUp(Member member) {
        EditTime editTime = new EditTime();
        editTime.setCreateTime(LocalDateTime.now());
        member.setEditTime(editTime);

        // 중복 확인
        memberRepository.findByEmail(member.getEmail())
                .ifPresent((findMember) -> {
                            throw new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_DUPLICATED);
                        }
                );

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_NON_NULL_PROPERTY);
        } catch (Exception e) {
            throw new CommonException(CustomExceptionStatus.COMMON_EXCEPTION);
        }
    }

    @Override
    public void drop(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        findMember.getEditTime().setRemoveTime(LocalDateTime.now());
        memberRepository.save(findMember);
    }

    @Override
    public Optional<Member> login(Member member) {
        Member loginMember = memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword())
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_FAILED));

        return Optional.of(loginMember);
    }

    @Override
    public Optional<Member> getMemberById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));
        return Optional.of(findMember);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        return Optional.of(findMember);
    }

    @Override
    @Transactional
    public Optional<Member> modify(Map<String, Object> paramMap) {
        Member modifyMember = memberRepository.findById(Long.parseLong(paramMap.get("id").toString()))
                .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL)
                );

        if (paramMap.containsKey("selected_mong_id")) {
            myMongRepository.findById(Long.parseLong(paramMap.get("selected_mong_id").toString()))
                    .ifPresent((myMong) -> paramMap.put("selectedMong", myMong));
        }

        if (paramMap.containsKey("selected_item_id")) {
            myItemRepository.findById(Long.parseLong(paramMap.get("selected_item_id").toString()))
                    .ifPresentOrElse(
                            (myItem) -> paramMap.put("selectedItem", myItem.toMyItemDto()),
                            () -> {
                                throw new CommonException(CustomExceptionStatus.ITEM_NOT_FOUND);
                            }
                    );
        }

//        추후 아이템 / 배경 구현 시 주석 해제
//        if (paramMap.containsKey("selected_background_id")) {
//            paramMap.put("selectedBackground", myBackgroundRepository.findById(
//                    Long.parseLong(paramMap.get("selected_background_id").toString())));
//        }

        modifyMember.modifyValue(paramMap);
        modifyMember.getEditTime().setUpdateTime(LocalDateTime.now());
        return Optional.of(memberRepository.save(modifyMember));
    }
}
