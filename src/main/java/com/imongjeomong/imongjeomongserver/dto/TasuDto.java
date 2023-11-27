package com.imongjeomong.imongjeomongserver.dto;

import com.imongjeomong.imongjeomongserver.entity.Tasu;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TasuDto {
    private Long tasuId;
    private Double lat;
    private Double lng;
    private String kioskId;
    private String address;

    public static TasuDto toTasuDto(Tasu tasu) {
        return TasuDto.builder()
                .tasuId(tasu.getId())
                .lat(tasu.getLat())
                .lng(tasu.getLng())
                .kioskId(tasu.getKioskId())
                .address(tasu.getAddress())
                .build();
    }
}
