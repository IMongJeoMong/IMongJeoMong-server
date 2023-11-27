package com.imongjeomong.imongjeomongserver.background.model.service;

import com.imongjeomong.imongjeomongserver.background.model.repository.BackgroundRepository;
import com.imongjeomong.imongjeomongserver.background.model.repository.MyBackgroundRepository;
import com.imongjeomong.imongjeomongserver.dto.BackgroundDto;
import com.imongjeomong.imongjeomongserver.dto.MyBackgroundDto;
import com.imongjeomong.imongjeomongserver.entity.Background;
import com.imongjeomong.imongjeomongserver.entity.MyBackground;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
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

        backgroundRepository.findAll().forEach(background ->
                backgroundDtoList.add(background.toBackgroundDto())
        );

        return backgroundDtoList;
    }

    @Override
    public List<MyBackgroundDto> getMyBackgroundList(Long memberId) {
        List<MyBackgroundDto> myBackgroundDtoList = new ArrayList<>();
        myBackgroundRepository.findAllByMemberId(memberId).forEach(myBackground ->
            myBackgroundDtoList.add(myBackground.toMyBackgroundDto())
        );
        return myBackgroundDtoList;
    }

    @Override
    public BackgroundDto getBackgroundById(Long backgroundId) {
        Background background = backgroundRepository.findById(backgroundId).orElseThrow(
                () -> new CommonException(CustomExceptionStatus.BACKGROUND_NOT_FOUND)
        );
        return background.toBackgroundDto();
    }

    @Override
    public MyBackgroundDto getSelectedBackgroundById(Long memberId, Long myBackgroundId) {
        MyBackground myBackground = myBackgroundRepository.findByIdAndMemberId(myBackgroundId, memberId)
                .orElseThrow(() -> new CommonException(CustomExceptionStatus.BACKGROUND_NOT_FOUND));
        return myBackground.toMyBackgroundDto();
    }
}
