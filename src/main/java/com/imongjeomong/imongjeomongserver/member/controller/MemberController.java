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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public CommonResponse drop(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        memberService.drop(jwtUtil.getMemberId(accessToken));
        return new CommonResponse(200, "회원탈퇴가 완료되었습니다.");
    }

    /* 로그인 */
    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody Member member) {
        Member loginMember = memberService.login(member)
                                            .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_EXCEPTION));

        DataResponse<Map> dataResponse = new DataResponse<>(200, "인증되었습니다.");
        dataResponse.setData(getToken(loginMember.getId()));
        return dataResponse;
    }

    /* 회원조회 */
    @GetMapping("/{email}")
    public DataResponse<?> getMemberInfo(@PathVariable String email){
        Member findMember = memberService.getMemberByEmail(email).orElseThrow(
                () -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));
        findMember.privateInformationProcessing();

        DataResponse<Member> response = new DataResponse<>(200, "회원 정보가 조회되었습니다.");
        response.setData(findMember);
        return response;
    }

    /* 토큰 재발급 */
    @GetMapping("/refresh")
    public DataResponse<?> refreshToken(HttpServletRequest request){
        String refreshToken = request.getHeader("Authorization").split(" ")[1];
        jwtUtil.checkToken(refreshToken);

        DataResponse<Map> dataResponse = new DataResponse<>(200, "토큰이 재발급 되었습니다.");
        dataResponse.setData(getToken(jwtUtil.getMemberId(refreshToken)));
        return dataResponse;
    }

    /* 토큰 발급 */
    public Map<String, String> getToken(Long memberId) {
        Map<String, String> result = new HashMap<>();
        result.put("access-token", jwtUtil.createAccessToken(memberId));
        result.put("refresh-token", jwtUtil.createRefreshToken(memberId));

        return result;
    }

}
