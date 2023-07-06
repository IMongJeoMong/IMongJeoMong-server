package com.imongjeomong.imongjeomongserver.background.model.service;

import com.imongjeomong.imongjeomongserver.background.model.repository.BackgroundRepository;
import com.imongjeomong.imongjeomongserver.background.model.repository.MyBackgroundRepository;
import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackgroundServiceImpl implements BackgroundService {

    private final BackgroundRepository backgroundRepository;
    private final MyBackgroundRepository myBackgroundRepository;
    private final JwtUtil jwtUtil;

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

    @Override
    public List<MyBackgroundDto> getMyBackgroundList(HttpServletRequest request) {
        List<MyBackgroundDto> myBackgroundDtoList = new ArrayList<>();
        Long memberId = jwtUtil.getMemberId(jwtUtil.getAccessToken(request));
        myBackgroundRepository.findAllByMemberId(memberId).stream().forEach(myBackground -> {
            MyBackgroundDto myBackgroundDto = MyBackgroundDto.builder()
                    .myBackgroundId(myBackground.getId())
                    .name(myBackground.getBackground().getName())
                    .price(myBackground.getBackground().getPrice())
                    .imagePath(myBackground.getBackground().getImagePath())
                    .build();
            myBackgroundDtoList.add(myBackgroundDto);
        });
        return myBackgroundDtoList;
    }
}
