package com.imongjeomong.imongjeomongserver.mong.model.service;

import com.imongjeomong.imongjeomongserver.dto.MyMongDto;
import com.imongjeomong.imongjeomongserver.entity.Mong;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MongService {
    List<Mong> getMongList(HttpServletRequest request);

    Mong getMongById(Long mongId);

    List<MyMongDto> getMyMongList(Long memberId);
}
