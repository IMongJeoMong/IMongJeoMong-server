package com.imongjeomong.imongjeomongserver.attraction.controller;

import com.imongjeomong.imongjeomongserver.attraction.model.service.AttractionService;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

}
