package com.imongjeomong.imongjeomongserver.attraction.model.service;

import com.imongjeomong.imongjeomongserver.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AttractionService {

    Page<Attraction> getAttractionList(Map<String, Object> paramMap, Pageable pageable);
}
