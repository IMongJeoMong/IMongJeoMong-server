package com.imongjeomong.imongjeomongserver.tasu.model.service;

import com.imongjeomong.imongjeomongserver.dto.TasuDto;
import com.imongjeomong.imongjeomongserver.entity.Tasu;
import com.imongjeomong.imongjeomongserver.tasu.model.repository.TasuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TasuServiceImpl implements TasuService {

    private final TasuRepository tasuRepository;

    @Override
    public List<TasuDto> getTasuList() {
        List<Tasu> tasuList = tasuRepository.findAll();

        List<TasuDto> tasuDtoList = new ArrayList<>();
        for (Tasu tasu : tasuList) {
            tasuDtoList.add(TasuDto.toTasuDto(tasu));
        }

        return tasuDtoList;
    }
}
