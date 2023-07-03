package com.imongjeomong.imongjeomongserver.item.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Item;
import com.imongjeomong.imongjeomongserver.entity.MyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyItemRepository extends JpaRepository<MyItem, Long> {

    List<MyItem> findByMemberId(Long memberId);

    @Query("select m from MyItem m where m.item.id = :itemId")
    Optional<MyItem> findByItem(@Param("itemId") Long itemId);

    @Query("select count(m) from MyItem m where m.member.id = :member_id")
    Long countByMemberId(@Param("member_id") Long memberId);
}
