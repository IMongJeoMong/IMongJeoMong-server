package com.imongjeomong.imongjeomongserver.item.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Item;
import com.imongjeomong.imongjeomongserver.entity.MyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyItemRepository extends JpaRepository<MyItem, Long> {

    List<MyItem> findByMemberId(Long memberId);
}
