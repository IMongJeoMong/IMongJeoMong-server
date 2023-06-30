package com.imongjeomong.imongjeomongserver.opendata.attraction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionOpenDataService {

    private final AttractionOpenDataRepository attractionOpenDataRepository;

    private final String attractionOpenDataUrl = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
    @Value("${openData.attraction.serviceKey}")
    private String serviceKey;
    private final OkHttpClient client = new OkHttpClient();

    /**
     * jpa bulk insert를 빠르게 하는 방법이 필요
     * 현재는 200개의 insert가 있다면 200번의 쿼리가 발생
     */
    @Transactional
    public void saveAttractionOpenData() throws UnsupportedEncodingException {
        // 기존 데이터 제거
        attractionOpenDataRepository.deleteAll();
        log.info("attraction 데이터 삭제 완료");

        // 주차장 데이터 total count 가져오기
        int count = getAttractionTotalCount();
        log.info("attraction count = {}", count);

        // 가져온 total count를 통해 모든 데이터 불러오기
        List<Attraction> attractionList = getAllAttractionInfo(count);

        setAlLAttractionDescription(attractionList);

        for (Attraction attraction : attractionList) {
            System.out.println(attraction);
        }

        // 불러온 모든 주차장 데이터를 db에 저장하기
        for (int i = 0; i < attractionList.size(); i++) {
            attractionOpenDataRepository.save(attractionList.get(i));
        }
    }

    /**
     * 공공데이터의 관광지 위치 개수를 리턴하는 메서드
     */
    private int getAttractionTotalCount() {
        String requestUrl = attractionOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&areaCode=3";
        System.out.println(requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();

            return jsonResponse.getAsJsonObject("response")
                    .getAsJsonObject("body")
                    .getAsJsonPrimitive("totalCount").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 관광지 공공데이터를 호출해 관광지 리스트로 반환하는 메서드
     */
    private List<Attraction> getAllAttractionInfo(int totalCont) {
        String requestUrl = attractionOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=" + totalCont + "&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A&areaCode=3";
        System.out.println(requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string())
                    .getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonObject("body")
                    .getAsJsonObject("items");

            JsonArray array = jsonResponse.getAsJsonArray("item");

            List<Attraction> attractionList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                Attraction attraction = new Attraction();

                // name, address, lat, lng, description, contentId, contentTypeId, imagePath, tel, sidoCode
                attraction.setName(array.get(i).getAsJsonObject().get("title").getAsString());
                attraction.setId(Long.parseLong(array.get(i).getAsJsonObject().get("contentid").getAsString()));
                attraction.setContentTypeId(Integer.parseInt(array.get(i).getAsJsonObject().get("contenttypeid").getAsString()));
                attraction.setLat(Double.parseDouble(array.get(i).getAsJsonObject().get("mapy").getAsString()));
                attraction.setLng(Double.parseDouble(array.get(i).getAsJsonObject().get("mapx").getAsString()));
                attraction.setAddress(array.get(i).getAsJsonObject().get("addr1").getAsString());
                attraction.setTel(array.get(i).getAsJsonObject().get("tel").getAsString());
                attraction.setImagePath(array.get(i).getAsJsonObject().get("firstimage").getAsString());
                attraction.setSidoCode(Integer.parseInt(array.get(i).getAsJsonObject().get("areacode").getAsString()));

                // gold, exp 설정
                setAttractionGoldAndExp(attraction);

                attractionList.add(attraction);
            }

            return attractionList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 각 관광지별 골드와 경험치 설정
     * 현재는 100G 와 300EXP를 설정해 놓았지만 향후 비즈니스 로직에 따라 변경 가능
     */
    private void setAttractionGoldAndExp(Attraction attraction) {
        attraction.setGold(100);
        attraction.setExp(300);
    }

    /**
     * description 데이터를 삽입한다.
     */
    private void setAlLAttractionDescription(List<Attraction> attractionList) {
        String requestUrl = attractionOpenDataUrl + "?serviceKey=" + serviceKey + "&MobileOS=ETC&MobileApp=AppTest&_type=json&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&numOfRows=10&pageNo=1&contentId=";
        requestUrl = requestUrl.replaceAll("areaBasedList1", "detailCommon1");

        for (Attraction attraction : attractionList) {
            Long contentId = attraction.getId();

            String contentRequestUrl = requestUrl + contentId;

            System.out.println(contentRequestUrl);
            Request request = new Request.Builder()
                    .url(contentRequestUrl)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                JsonObject jsonResponse = JsonParser.parseString(response.body().string())
                        .getAsJsonObject()
                        .getAsJsonObject("response")
                        .getAsJsonObject("body")
                        .getAsJsonObject("items");

                JsonArray array = jsonResponse.getAsJsonArray("item");

                attraction.setDescription(array.get(0).getAsJsonObject().get("overview").getAsString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
