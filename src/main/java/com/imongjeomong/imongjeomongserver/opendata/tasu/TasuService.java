package com.imongjeomong.imongjeomongserver.opendata.tasu;

import com.google.gson.*;
import com.imongjeomong.imongjeomongserver.entity.Tasu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
@Slf4j
public class TasuService {

    private final String tasuOpenDataUrl = "https://apis.data.go.kr/6300000/openapi2022/tasuInfo/gettasuInfo";
    private final TasuRepository tasuRepository;

    @Value("${openData.tasu.serviceKey}")
    private String serviceKey;
    private final OkHttpClient client = new OkHttpClient();

    /**
     * jpa bulk insert를 빠르게 하는 방법이 필요
     * 현재는 200개의 insert가 있다면 200번의 쿼리가 발생
     */
    @Transactional
    public void saveTasuInfo() throws UnsupportedEncodingException {
        // 기존 데이터 제거
        tasuRepository.deleteAll();

        // tasu 데이터 total count 가져오기
        int count = getTasuTotalCount();

        // 가져온 total count를 통해 모든 데이터 불러오기
        List<Tasu> tasuInfo = getAllTasuInfo(count);


        // 불러온 모든 tasu 데이터를 db에 저장하기
        for (int i = 0; i < tasuInfo.size(); i++) {
            tasuRepository.save(tasuInfo.get(i));
        }

        log.debug("Tasu 데이터 저장 완료");
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

            log.info("tasu total count = {}", array.size());

            List<Tasu> tasuList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                Tasu tasu = new Tasu();

                // lat, lng, kioskId, address
                tasu.setLat(Double.parseDouble(array.get(i).getAsJsonObject().get("laCrdnt").getAsString()));
                tasu.setLng(Double.parseDouble(array.get(i).getAsJsonObject().get("loCrdnt").getAsString()));
                tasu.setAddress(array.get(i).getAsJsonObject().get("adres").getAsString());
                tasu.setKioskId(array.get(i).getAsJsonObject().get("kioskId").getAsString());

                tasuList.add(tasu);
            }

            return tasuList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
