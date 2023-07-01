package com.imongjeomong.imongjeomongserver.attraction.model.service;

import com.imongjeomong.imongjeomongserver.attraction.model.repository.AttractionRepository;
import com.imongjeomong.imongjeomongserver.attraction.model.repository.MyAttractionRepository;
import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService{

    private final AttractionRepository attractionRepository;
    private final MyAttractionRepository myAttractionRepository;

    @Override
    public Page<AttractionDTO> getAttractionList(Map<String, Object> paramMap, Pageable pageable) {
        Double lat = Double.parseDouble(paramMap.get("lat").toString());
        Double lng = Double.parseDouble(paramMap.get("lng").toString());
        String keyword = paramMap.get("keyword").toString();
        return attractionRepository.findAttractionByCondition(keyword, lat, lng, pageable);
    }

    @Override
    public AttractionDTO getAttractionInfo(Long attractionId) {
        Attraction attraction = attractionRepository.findById(attractionId).
                orElseThrow(() -> new CommonException(CustomExceptionStatus.ATTRACTION_NOT_FOUND));

        AttractionDTO attractionDTO = AttractionDTO.builder()
                .id(attraction.getId())
                .name(attraction.getName())
                .address(attraction.getAddress())
                .lat(attraction.getLat())
                .lng(attraction.getLng())
                .description(attraction.getDescription())
                .contentTypeId(attraction.getContentTypeId())
                .imagePath(attraction.getImagePath())
                .tel(attraction.getTel())
                .sidoCode(attraction.getSidoCode())
                .gold(attraction.getGold())
                .exp(attraction.getExp()).build();
        return attractionDTO;
    }
}
