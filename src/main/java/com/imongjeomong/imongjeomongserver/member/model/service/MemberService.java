package com.imongjeomong.imongjeomongserver.member.model.service;


import com.imongjeomong.imongjeomongserver.entity.Member;

import java.util.Map;
import java.util.Optional;

public interface MemberService {
    void signUp(Member member);
    void drop(Long memberId);
    Optional<Member> login(Member member);
    Optional<Member> getMemberById(Long memberId);
    Optional<Member> getMemberByEmail(String email);
    Optional<Member> modify(Map<String, Object> paramMap);
}
