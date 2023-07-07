package com.imongjeomong.imongjeomongserver.attraction.model.service;

import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.dto.MyAttractionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AttractionService {

    Page<AttractionDTO> getAttractionList(Map<String, Object> paramMap, Pageable pageable);

    AttractionDTO getAttractionInfo(Long attractionId);

    List<MyAttractionDTO> getMyAttractionList(HttpServletRequest request, Pageable pageable);

    void visitAttraction(Long attractionId, HttpServletRequest request, Map<String, Object> paramMap);
}
