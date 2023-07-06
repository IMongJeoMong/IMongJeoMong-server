package com.imongjeomong.imongjeomongserver.background.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Long> {

}
