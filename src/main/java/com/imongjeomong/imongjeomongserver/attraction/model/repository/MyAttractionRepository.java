package com.imongjeomong.imongjeomongserver.attraction.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyAttractionRepository extends JpaRepository<MyAttraction, Long> {
}
