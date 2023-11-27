package com.imongjeomong.imongjeomongserver.mong.model.service;

import com.imongjeomong.imongjeomongserver.dto.MyMongDto;
import com.imongjeomong.imongjeomongserver.entity.Mong;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.MongException;
import com.imongjeomong.imongjeomongserver.mong.model.repository.MongRepository;
import com.imongjeomong.imongjeomongserver.mong.model.repository.MyMongRepository;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongServiceImpl implements MongService{

    private final MongRepository mongRepository;
    private final MyMongRepository myMongRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<Mong> getMongList(HttpServletRequest request) {
        String accessToken = "";
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
        }catch (NullPointerException e){
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        return mongRepository.findAll();
    }

    @Override
    public List<MyMongDto> getMyMongList(Long memberId) {
        log.info("=== {} === ", memberId);
        List<MyMongDto> myMongDtoList = new ArrayList<>();
        myMongRepository.findAllByMemberId(memberId).stream().forEach(
                (myMong) -> myMongDtoList.add(myMong.toMyMongDto())
        );

        return myMongDtoList;
    }


    @Override
    public Mong getMongById(Long mongId) {
        Mong findMong = mongRepository.findById(mongId).orElseThrow(
                () -> new MongException(CustomExceptionStatus.MONG_DOES_NOT_EXISTS)
        );
        return findMong;
    }


}
