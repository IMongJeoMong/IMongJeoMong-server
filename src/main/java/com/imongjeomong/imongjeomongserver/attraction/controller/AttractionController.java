package com.imongjeomong.imongjeomongserver.attraction.controller;

import com.imongjeomong.imongjeomongserver.attraction.model.service.AttractionService;
import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    /* 관광지 리스트 반환 */
    @GetMapping("/list")
    public DataResponse<?> getAttractionList(@RequestParam Map<String, Object> paramMap, Pageable pageable) {
        DataResponse<List> dataResponse = new DataResponse<>(200, "관광지가 조회되었습니다.");
        dataResponse.setData(attractionService.getAttractionList(paramMap, pageable).getContent());

        return dataResponse;
    }

    /* 관광지 상세 조회 */
    @GetMapping("/{attractionId}")
    public DataResponse<?> getAttractionInfo(@PathVariable Long attractionId){
        DataResponse<AttractionDTO> dataResponse = new DataResponse<>(200, "관광지가 조회되었습니다.");
        dataResponse.setData(attractionService.getAttractionInfo(attractionId));
        return dataResponse;
    }

}