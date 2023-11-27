package com.imongjeomong.imongjeomongserver.item.model.repository;

import com.imongjeomong.imongjeomongserver.entity.MyItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MyItemRepository extends JpaRepository<MyItem, Long> {

    List<MyItem> findByMemberId(Long memberId);

    @Query("select m from MyItem m where m.item.id = :itemId and m.member.id = :memberId")
    Optional<MyItem> findByItem(@Param("itemId") Long itemId, @Param("memberId") Long memberId);

    Optional<MyItem> findByItemIdAndMemberId(Long itemId, Long memberId);

    @Query("select count(m) from MyItem m where m.member.id = :member_id")
    Long countByMemberId(@Param("member_id") Long memberId);

    @NotNull
    @Query("SELECT m from MyItem m JOIN FETCH m.item WHERE m.id = :myItemId")
    Optional<MyItem> findById(@NotNull Long myItemId);
}
