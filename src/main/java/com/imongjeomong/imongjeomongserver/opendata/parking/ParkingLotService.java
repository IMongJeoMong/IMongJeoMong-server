package com.imongjeomong.imongjeomongserver.opendata.parking;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imongjeomong.imongjeomongserver.entity.ParkingLot;
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
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    private final String parkingOpenDataUrl = "https://challenge.daejeon.go.kr/restapi/openapi/smart_on/parking/info";
    @Value("${openData.parkingLot.serviceKey}")
    private String serviceKey;
    private final OkHttpClient client = new OkHttpClient();

    /**
     * jpa bulk insert를 빠르게 하는 방법이 필요
     * 현재는 200개의 insert가 있다면 200번의 쿼리가 발생
     */
    @Transactional
    public void saveParkingLotInfo() throws UnsupportedEncodingException {
        // 기존 데이터 제거
        parkingLotRepository.deleteAll();
        log.info("Parking lot 데이터 삭제 완료");

        // 주차장 데이터 total count 가져오기
        int count = getParkingLotTotalCount();
        log.info("Parking lot count = {}", count);

        // 가져온 total count를 통해 모든 데이터 불러오기
        List<ParkingLot> parkingLotList = getAllParkingLotInfo(count);


        // 불러온 모든 주차장 데이터를 db에 저장하기
        for (int i = 0; i < parkingLotList.size(); i++) {
            parkingLotRepository.save(parkingLotList.get(i));
        }
    }

    /**
     * 공공데이터의 주차장의 위치 개수를 리턴하는 메서드
     */
    private int getParkingLotTotalCount() {
        String requestUrl = parkingOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=1";
        System.out.println(requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            return jsonResponse.get("totalCount").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 주차장의 모든 정보를 반환하는 메서드
     */
    private List<ParkingLot> getAllParkingLotInfo(int totalCont) {
        String requestUrl = parkingOpenDataUrl + "?serviceKey=" + serviceKey + "&pageNo=1&numOfRows=" + totalCont;
        System.out.println(requestUrl);

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        // 응답 결과 반환
        try (Response response = client.newCall(request).execute()) {
            JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
            JsonArray array = jsonResponse.getAsJsonArray("resultList");

            System.out.println("array.size() = " + array.size());

            List<ParkingLot> parkingLotList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                System.out.println(i);
                ParkingLot parkingLot = new ParkingLot();

                // lat, lng, kioskId, address
                parkingLot.setName(array.get(i).getAsJsonObject().get("park_name").getAsString());
                parkingLot.setLat(Double.parseDouble(array.get(i).getAsJsonObject().get("park_latitude").getAsString()));
                parkingLot.setLng(Double.parseDouble(array.get(i).getAsJsonObject().get("park_longitude").getAsString()));

                /**
                 * 공공데이터 중 지번 address가 null 인 경우 도로명으로 입력.
                 * 도로명도 null이라면 주소값을 비워둔다.
                 * -> 추후 위,경도와 맵api를 활용해 주소를 가져오는 로직이 필요
                 */
                if ("null".equals(array.get(i).getAsJsonObject().get("park_address1").toString())) {
                    if ("null".equals(array.get(i).getAsJsonObject().get("park_address2").toString())) {
                        parkingLot.setAddress(null);
                    } else {
                        parkingLot.setAddress(array.get(i).getAsJsonObject().get("park_address2").getAsString());
                    }
                } else {
                    parkingLot.setAddress(array.get(i).getAsJsonObject().get("park_address1").getAsString());
                }

                parkingLotList.add(parkingLot);
                System.out.println(parkingLot);
            }

            return parkingLotList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
