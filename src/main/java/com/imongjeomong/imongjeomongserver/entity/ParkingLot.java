package com.imongjeomong.imongjeomongserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "PARKING_LOT")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double lat;
    private Double lng;
    private String address;

    /*
    사용 가능한 데이터 필드 목록 - 필요 시 추가 가능

    프로젝트 사용 데이터 필드 - park_name, park_latitude, park_longitude, pard_address2
    {
        "collect_time": "2023-06-26 00:00:00",
        "time_stamp": "2023-06-26 00:00:00",
        "park_seq": 1,
        "park_name": "대전 중구 선병원 2호점 [대전선병원 3주차장]",
        "park_latitude": 36.33592324872599,
        "park_longitude": 127.4108042585646,
        "park_zip": "34815",
        "park_full_address": "대전 중구 목동 10-26",
        "park_address1": "대전 중구 목동 10-26",
        "park_address2": "대전광역시 중구 목중로25번길 7",
        "park_class_cd": "02",
        "park_region_cd": "042",
        "park_phone1": "02-1588-5783",
        "park_phone2": null,
        "park_phone3": null,
        "park_type_cd": "01",
        "park_division_cd": "01",
        "park_biz_opentime": "00:00",
        "park_biz_closetime": "24:00",
        "park_sat_biz_opentime": "00:00",
        "park_sat_biz_closetime": "24:00",
        "park_sun_hol_opentime": null,
        "park_sun_hol_closetime": null,
        "park_interval_free_yn": "N",
        "park_basic_interval_minute": 60,
        "park_basic_interval_price": 0,
        "park_additional_interval_minute": 15,
        "park_additional_interval_price": 300,
        "park_oneday_price": 0,
        "park_extra_basic_interval_minute": 0,
        "park_extra_additional_interval_minute": 0,
        "park_extra_interval_price": 0,
        "park_biz_hour_cd": "01",
        "park_app_vision_available_car": 1,
        "park_total_cnt": 24,
        "park_discountticket_use_yn": "N",
        "park_incarpayment_use_yn": "N",
        "park_app_pay_yn": "N"
    }
     */
}
