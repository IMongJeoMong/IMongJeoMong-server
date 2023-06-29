package com.imongjeomong.imongjeomongserver.mong.model.service;

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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongServiceImpl implements MongService{

    private final MongRepository mongRepository;
    private final MyMongRepository myMongRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Map<String, Object> getMongList(HttpServletRequest request) {
        String accessToken = "";
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
        }catch (NullPointerException e){
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("mongList", mongRepository.findAll());
        map.put("myMongList", myMongRepository.findAllByMemberId(jwtUtil.getMemberId(accessToken)));

        return map;
    }

    @Override
    public Mong getMongById(Long mongId) {
        Mong findMong = mongRepository.findById(mongId).orElseThrow(
                () -> new MongException(CustomExceptionStatus.MONG_DOES_NOT_EXISTS)
        );
        return findMong;
    }
}
