package com.imongjeomong.imongjeomongserver.opendata.attraction;

import com.imongjeomong.imongjeomongserver.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionOpenDataRepository extends JpaRepository<Attraction, Long> {
}
