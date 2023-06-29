package com.imongjeomong.imongjeomongserver.attraction.model.service;

import com.imongjeomong.imongjeomongserver.attraction.model.repository.AttractionRepository;
import com.imongjeomong.imongjeomongserver.attraction.model.repository.MyAttractionRepository;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
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
    public Page<Attraction> getAttractionList(Map<String, Object> paramMap, Pageable pageable) {
        Double lat = Double.parseDouble(paramMap.get("lat").toString());
        Double lng = Double.parseDouble(paramMap.get("lng").toString());
        String keyword = paramMap.get("keyword").toString();
//        return attractionRepository.findAttractionByCondition(keyword, lat, lng, pageable);
        return attractionRepository.findAttractionByCondition(keyword, lat, pageable);
//        return attractionRepository.findAttractionByCondition(keyword, pageable);
    }
}
