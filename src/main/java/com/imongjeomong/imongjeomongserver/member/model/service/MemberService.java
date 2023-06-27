package com.imongjeomong.imongjeomongserver.member.model.service;


import com.imongjeomong.imongjeomongserver.entity.Member;

import java.util.Optional;

public interface MemberService {
    void signUp(Member member);

    void drop();

    Optional<Member> login(Member member);

    Optional<Member> getUser(Long memberId);

    void modify(Member member);

}
