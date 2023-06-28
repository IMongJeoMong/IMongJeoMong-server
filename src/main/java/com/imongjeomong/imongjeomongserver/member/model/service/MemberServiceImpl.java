package com.imongjeomong.imongjeomongserver.member.model.service;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.entity.common.EditTime;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

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
    public void modify(Member member) {
        Member modifyMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> {
                    throw new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL);
                }
        );
        modifyMember.modifyValue(member);
        memberRepository.save(modifyMember);
    }
}
