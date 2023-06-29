package com.imongjeomong.imongjeomongserver.mong.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Mong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongRepository extends JpaRepository<Mong, Long> {

}
