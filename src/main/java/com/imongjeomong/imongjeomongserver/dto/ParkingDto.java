package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.ParkingLot;
import com.imongjeomong.imongjeomongserver.entity.Tasu;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDto {
    private Long parkingId;
    private String name;
    private Double lat;
    private Double lng;
    private String address;

    public static ParkingDto toParkingDto(ParkingLot parkingLot) {
        return ParkingDto.builder()
                .parkingId(parkingLot.getId())
                .name(parkingLot.getName())
                .lat(parkingLot.getLat())
                .lng(parkingLot.getLng())
                .address(parkingLot.getAddress())
                .build();
    }
}
