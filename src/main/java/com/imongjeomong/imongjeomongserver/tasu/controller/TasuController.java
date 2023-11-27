package com.imongjeomong.imongjeomongserver.tasu.controller;

import com.imongjeomong.imongjeomongserver.dto.TasuDto;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.tasu.model.service.TasuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TasuController {

    private final TasuService tasuService;

    @GetMapping("/tasu/list")
    public DataResponse<List<TasuDto>> getTasuList() {
        List<TasuDto> tasuList = tasuService.getTasuList();

        DataResponse<List<TasuDto>> response = new DataResponse<>(200, "타슈 리스트 조회 성공");
        response.setData(tasuList);
        return response;
    }
}
