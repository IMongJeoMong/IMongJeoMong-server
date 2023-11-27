package com.imongjeomong.imongjeomongserver.naver;

import com.imongjeomong.imongjeomongserver.dto.NaverBlogItem;
import com.imongjeomong.imongjeomongserver.util.NaverApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NaverController {

    private final NaverApiUtil naverApiUtil;

    @GetMapping("/naver/blog/{query}")
    public Map<String, List<?>> getBlogList(@PathVariable String query) {
        List<NaverBlogItem> blogList = naverApiUtil.getBlogList(query);

        Map<String, List<?>> response = new HashMap<>();
        response.put("items", blogList);
        return response;
    }
}
