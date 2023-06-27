package com.imongjeomong.imongjeomongserver.member.controller;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.member.model.service.MemberService;
import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    /* 회원가입 */
    @PostMapping("/signup")
    public CommonResponse signUp(@RequestBody Member member) {
        memberService.signUp(member);

        return new CommonResponse(201, "회원가입이 완료되었습니다.");
    }

    /* 회원탈퇴 */
    @DeleteMapping("/drop")
    public CommonResponse drop() {
        memberService.drop();

        return new CommonResponse(200, "회원탈퇴가 완료되었습니다.");
    }

    /* 로그인 */
    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody Member member) {
        Member loginMember = memberService.login(member)
                                            .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_EXCEPTION));

        Map<String, String> result = new HashMap<>();
        result.put("access-token", jwtUtil.createAccessToken(loginMember.getId()));
        result.put("refresh-token", jwtUtil.createRefreshToken(loginMember.getId()));

        DataResponse<Map> dataResponse = new DataResponse<>(200, "인증되었습니다.");
        dataResponse.setData(result);
        return dataResponse;
    }
}
