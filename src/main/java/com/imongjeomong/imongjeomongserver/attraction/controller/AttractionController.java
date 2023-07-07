package com.imongjeomong.imongjeomongserver.attraction.controller;

import com.imongjeomong.imongjeomongserver.attraction.model.service.AttractionService;
import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.quest.model.service.QuestService;
import com.imongjeomong.imongjeomongserver.response.CommonResponse;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;
    private final QuestService questServiceImpl;

    /* 관광지 리스트 반환 */
    @GetMapping("/list")
    public DataResponse<?> getAttractionList(@RequestParam Map<String, Object> paramMap, Pageable pageable) {
        DataResponse<List> dataResponse = new DataResponse<>(200, "관광지가 조회되었습니다.");
        dataResponse.setData(attractionService.getAttractionList(paramMap, pageable).getContent());

        return dataResponse;
    }

    /* 방문한 관광지 리스트 반환 */
    @GetMapping("/visited")
    public DataResponse<?> getMyAttractionList(HttpServletRequest request, Pageable pageable){
        DataResponse<List> dataResponse = new DataResponse<>(200, "방문했던 관광지가 조회되었습니다.");
        dataResponse.setData(attractionService.getMyAttractionList(request, pageable).getContent());
        return dataResponse;
    }

    /* 관광지 상세 조회 */
    @GetMapping("/{attractionId}")
    public DataResponse<?> getAttractionInfo(@PathVariable Long attractionId){
        DataResponse<AttractionDTO> dataResponse = new DataResponse<>(200, "관광지가 조회되었습니다.");
        dataResponse.setData(attractionService.getAttractionInfo(attractionId));
        return dataResponse;
    }

    /* 관광지 방문 */
    @PostMapping("/visit/{attractionId}")
    public CommonResponse visitAttraction(@PathVariable Long attractionId, @RequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        attractionService.visitAttraction(attractionId, request, paramMap);
        CommonResponse response = new CommonResponse(200, "방문 처리되었습니다.");

        questServiceImpl.attendAttraction(request);
        return response;
    }

}
