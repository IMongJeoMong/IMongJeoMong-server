package com.imongjeomong.imongjeomongserver.opendata.tasu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TasuRepository extends JpaRepository<Tasu, Long> {

}
