package com.imongjeomong.imongjeomongserver.background.model.service;

import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;

import java.util.List;

public interface BackgroundService {
    List<BackgroundDto> getBackgroundList();

    List<MyBackgroundDto> getMyBackgroundList(Long memberId);

    BackgroundDto getBackgroundById(Long backgroundId);

    MyBackgroundDto getSelectedBackgroundById(Long memberId, Long myBackgroundId);
}
