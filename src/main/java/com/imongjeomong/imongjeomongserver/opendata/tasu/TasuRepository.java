package com.imongjeomong.imongjeomongserver.opendata.tasu;

import com.imongjeomong.imongjeomongserver.entity.Tasu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasuRepository extends JpaRepository<Tasu, Long> {

}
