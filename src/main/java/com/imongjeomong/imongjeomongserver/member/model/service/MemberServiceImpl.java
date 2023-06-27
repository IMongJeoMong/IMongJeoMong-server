package com.imongjeomong.imongjeomongserver.member.model.service;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void signUp(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void drop() {

    }

    @Override
    public Optional<Member> login(Member member) {
        return memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword());
    }

    @Override
    public Optional<Member> getUser(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public void modify(Member member) {

    }

}
