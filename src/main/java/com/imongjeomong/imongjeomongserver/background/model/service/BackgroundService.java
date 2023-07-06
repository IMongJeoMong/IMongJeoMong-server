package com.imongjeomong.imongjeomongserver.background.model.service;

import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BackgroundService {
    List<BackgroundDto> getBackgroundList();

    List<MyBackgroundDto> getMyBackgroundList(HttpServletRequest request);
}
