package com.imongjeomong.imongjeomongserver.mong.model.service;

import com.imongjeomong.imongjeomongserver.entity.Mong;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MongService {
    Map<String, Object> getMongList(HttpServletRequest request);

    Mong getMongById(Long mongId);
}
