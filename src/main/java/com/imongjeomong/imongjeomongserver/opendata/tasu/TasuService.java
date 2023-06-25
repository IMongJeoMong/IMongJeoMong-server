package com.imongjeomong.imongjeomongserver.opendata.tasu;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TasuService {

    private final String tasuOpenDataUrl = "https://apis.data.go.kr/6300000/openapi2022/tasuInfo/gettasuInfo";

    @Value("${openData.serviceKey}")
    private String serviceKey;
    private final OkHttpClient client = new OkHttpClient();

    public void saveTasuInfo() throws UnsupportedEncodingException {
        // https://apis.data.go.kr/6300000/openapi2022/tasuInfo/gettasuInfo?serviceKey={serviceKey}&pageNo=1&numOfRows={numOfRows}
        String serviceKey = "HGWbiXI4puxU2CzKvcLxn5CesxFWEHG%2F49E1OBVZH4N3flzq0q%2B442V4Ii2Bk43fuLm0jmkitNv5Qaxxb1U6ag%3D%3D";
        String requestUrl = tasuOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=1";

        // tasu 데이터 total count 가져오기
        int count = getTasuTotalCount();

        // 가져온 total count를 통해 모든 데이터 불러오기
        List<Tasu> tasuInfo = getAllTasuInfo(count);


        // 불러온 모든 tasu 데이터를 db에 저장하기

    }

    /**
     * 공공데이터의 Tasu 위치 개수를 리턴하는 메서드
     */
    private int getTasuTotalCount() {
        /**
         * RestTemplate 사용 할 때 REST API 호출 시 URL 인코딩 문제를 해결해야 함
         * [%가 들어간 부분이 호출시 %25로 자동 인코딩되는 문제 발생]
         *
         * -> RestTemplate 라이브러리를 okhttp3 라이브러리로 변경하여 해결
         */
        String requestUrl = tasuOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=1";

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            int count = Integer.parseInt(jsonResponse.getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonObject("body").get("totalCount").toString());
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<Tasu> getAllTasuInfo(int totalCont) {
        String requestUrl = tasuOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=" + totalCont;

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            JsonArray array = jsonResponse.getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonObject("body").getAsJsonArray("items");

            System.out.println(array.toString());
            System.out.println("array.size() = " + array.size());

            List<Tasu> tasuList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                Tasu tasu = new Tasu();

            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }
}
