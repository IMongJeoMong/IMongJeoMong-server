package com.imongjeomong.imongjeomongserver.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imongjeomong.imongjeomongserver.dto.NaverBlogItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverApiUtil {

    @Value("${naver.clientId}")
    private String clientKey;

    @Value("${naver.clientSecret}")
    private String clientSecret;

    @Value("${naver.url}")
    private String apiURL;

    private final OkHttpClient client = new OkHttpClient();

    public List<NaverBlogItem> getBlogList(String query) {
        String requestUrl = apiURL + "?query=" + query;

        Request request = new Request.Builder()
                .addHeader("X-Naver-Client-Id", clientKey)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .url(requestUrl)
                .get()
                .build();

        System.out.println(request.toString());

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            System.out.println(jsonResponse.toString());
            JsonArray array = jsonResponse.getAsJsonArray("items");
            System.out.println(array.toString());

            log.info("tasu total count = {}", array.size());

            List<NaverBlogItem> blogList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                NaverBlogItem item = new NaverBlogItem();

                item.setTitle(array.get(i).getAsJsonObject().get("title").getAsString());
                item.setLink(array.get(i).getAsJsonObject().get("link").getAsString());
                item.setBloggerlink(array.get(i).getAsJsonObject().get("bloggerlink").getAsString());
                item.setDescription(array.get(i).getAsJsonObject().get("description").getAsString());
                item.setBloggername(array.get(i).getAsJsonObject().get("bloggername").getAsString());
                item.setPostdate(array.get(i).getAsJsonObject().get("postdate").getAsString());

                blogList.add(item);
            }

            return blogList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
