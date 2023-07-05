package com.imongjeomong.imongjeomongserver.member.controller;

import com.imongjeomong.imongjeomongserver.dto.MemberDto;
import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.item.model.service.ItemService;
import com.imongjeomong.imongjeomongserver.member.model.service.MemberService;
import com.imongjeomong.imongjeomongserver.quest.model.service.QuestService;
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

    private final MemberService memberServiceImpl;
    private final ItemService itemService;
    private final QuestService questServiceImpl;
    private final JwtUtil jwtUtil;

    /* 회원가입 */
    @PostMapping("/signup")
    public CommonResponse signUp(@RequestBody Member member) {
        memberServiceImpl.signUp(member);
        return new CommonResponse(201, "회원가입이 완료되었습니다.");
    }

    /* 회원탈퇴 */
    @DeleteMapping("/drop")
    public CommonResponse drop(HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        memberServiceImpl.drop(jwtUtil.getMemberId(accessToken));
        return new CommonResponse(200, "회원탈퇴가 완료되었습니다.");
    }

    /* 로그인 */
    @PostMapping("/login")
    public DataResponse<?> login(@RequestBody Member member) {
        Member loginMember = memberServiceImpl.login(member).get();
        DataResponse<Map> dataResponse = new DataResponse<>(200, "인증되었습니다.");
        Map<String, Object> map = new HashMap<>();

        questServiceImpl.attendMember(loginMember.getId());

        map.put("Authorization", getToken(loginMember.getId()));

        MemberDto memberDto = MemberDto.builder()
                .memberId(loginMember.getId())
                .email(loginMember.getEmail())
                .nickname(loginMember.getNickname())
                .birth(loginMember.getBirth())
                .gender(loginMember.getGender())
                .sidoCode(loginMember.getSidoCode())
                .gold(loginMember.getGold())
                .selectedMong(loginMember.getSelectedMong().toMyMongDto())
                .selectedItem(itemService.getSelectedItemById(loginMember.getSelectedItemId()))
                .selectedBackground(null)
                .build();

        map.put("LoginMember", memberDto);
        dataResponse.setData(map);
        return dataResponse;
    }

    /* 회원조회 */
    @GetMapping("/{email}")
    public DataResponse<?> getMemberInfo(@PathVariable String email){
        Member findMember = memberServiceImpl.getMemberByEmail(email).get();

        DataResponse<MemberDto> response = new DataResponse<>(200, "회원 정보가 조회되었습니다.");

        MemberDto memberDto = MemberDto.builder()
                .memberId(findMember.getId())
                .email(findMember.getEmail())
                .nickname(findMember.getNickname())
                .birth(findMember.getBirth())
                .gender(findMember.getGender())
                .sidoCode(findMember.getSidoCode())
                .gold(findMember.getGold())
                .selectedMong(findMember.getSelectedMong().toMyMongDto())
                .selectedItem(itemService.getSelectedItemById(findMember.getSelectedItemId()))
                .selectedBackground(null)
                .build();
        response.setData(memberDto);
        return response;
    }

    /* 회원 정보 수정 */
    @PatchMapping("/modify")
    public DataResponse<?> modifyMember(@RequestBody Map<String, Object> paramMap, HttpServletRequest request){
        String accessToken = getAccessToken(request);
        paramMap.put("id", jwtUtil.getMemberId(accessToken));
        DataResponse<MemberDto> dataResponse = new DataResponse<>(200, "회원 정보가 수정되었습니다.");
        memberServiceImpl.modify(paramMap).ifPresent((modifyMember) -> {
            MemberDto modifyMemberDto = MemberDto.builder()
                    .memberId(modifyMember.getId())
                    .email(modifyMember.getEmail())
                    .nickname(modifyMember.getNickname())
                    .birth(modifyMember.getBirth())
                    .gender(modifyMember.getGender())
                    .sidoCode(modifyMember.getSidoCode())
                    .gold(modifyMember.getGold())
                    .selectedMong(modifyMember.getSelectedMong().toMyMongDto())
                    .selectedItem(itemService.getSelectedItemById(modifyMember.getSelectedItemId()))
                    .selectedBackground(null)
                    .build();

            dataResponse.setData(modifyMemberDto);
        });
        return dataResponse;
    }

    /* 토큰 재발급 */
    @GetMapping("/refresh")
    public DataResponse<?> refreshToken(HttpServletRequest request){
        String refreshToken = getAccessToken(request);
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

    /* 헤더 Authorization 파싱 */
    private static String getAccessToken(HttpServletRequest request) {
        String accessToken = "";
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
        }catch (NullPointerException e){
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }
        return accessToken;
    }

}
