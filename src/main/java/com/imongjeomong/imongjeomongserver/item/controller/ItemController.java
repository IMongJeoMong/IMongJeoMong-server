package com.imongjeomong.imongjeomongserver.item.controller;

import com.imongjeomong.imongjeomongserver.dto.ItemDto;
import com.imongjeomong.imongjeomongserver.exception.CommonException;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.item.model.service.ItemService;
import com.imongjeomong.imongjeomongserver.response.DataResponse;
import com.imongjeomong.imongjeomongserver.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final JwtUtil jwtUtil;

    /**
     * 현재 유저가 가지고 있는 아이템 반환
     */
    @GetMapping("/my-item/list")
    public DataResponse<List<ItemDto>> getMyItemList(HttpServletRequest request) {
        String accessToken = "";
        Long memberId = null;
        try {
            accessToken = request.getHeader("Authorization").split(" ")[1];
            memberId = jwtUtil.getMemberId(accessToken);
        } catch (NullPointerException e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        List<ItemDto> itemList = itemService.getMyItemList(memberId);

        DataResponse<List<ItemDto>> response = new DataResponse<>(200, "소유 중인 아이템 리스트");
        response.setData(itemList);

        return response;
    }

    /**
     * 전체 아이템 리스트 조회
     */
    @GetMapping("/item/list")
    public DataResponse<List<ItemDto>> getAllItemList() {
        List<ItemDto> itemList = itemService.getItemList();
        DataResponse<List<ItemDto>> response = new DataResponse<>(200, "전체 아이템 리스트");
        response.setData(itemList);

        return response;
    }

    @PostMapping("/item/buy")
    public DataResponse<Object> buyItem(HttpServletRequest request, @RequestBody ItemDto item) {
        Long memberId = null;
        try {
            String accessToken = request.getHeader("Authorization").split(" ")[1];
            memberId = jwtUtil.getMemberId(accessToken);
        } catch (Exception e) {
            throw new CommonException(CustomExceptionStatus.TOKEN_DOES_NOT_EXISTS);
        }

        ItemDto buyItem = itemService.buyItem(memberId, item.getItemId());
        DataResponse<Object> response = new DataResponse<>(201, "아이템 구매 성공");
        response.setData(buyItem);

        return response;
    }
}
