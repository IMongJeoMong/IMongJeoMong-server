package com.imongjeomong.imongjeomongserver.background.model.service;

import com.imongjeomong.imongjeomongserver.background.model.repository.BackgroundRepository;
import com.imongjeomong.imongjeomongserver.background.model.repository.MyBackgroundRepository;
import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackgroundServiceImpl implements BackgroundService{

    private final BackgroundRepository backgroundRepository;
    private final MyBackgroundRepository myBackgroundRepository;

    @Override
    public List<BackgroundDto> getBackgroundList() {
        List<BackgroundDto> backgroundDtoList = new ArrayList<>();

        backgroundRepository.findAll().stream().forEach(background -> {
                BackgroundDto backgroundDto = BackgroundDto.builder()
                        .backgroundId(background.getId())
                        .name(background.getName())
                        .price(background.getPrice())
                        .imagePath(background.getImagePath())
                        .build();
                backgroundDtoList.add(backgroundDto);
                }
        );
        return backgroundDtoList;
    }
}
