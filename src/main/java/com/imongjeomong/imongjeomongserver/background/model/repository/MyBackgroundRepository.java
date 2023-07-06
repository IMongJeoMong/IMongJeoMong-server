package com.imongjeomong.imongjeomongserver.background.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBackgroundRepository extends JpaRepository<MyBackground, Long> {

}
