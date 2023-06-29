package com.imongjeomong.imongjeomongserver.member.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m from Member m " +
            "JOIN FETCH m.selectedMong sm " +
            "JOIN FETCH sm.mong " +
//            "JOIN FETCH m.selectedBackground sb " +
//            "JOIN FETCH sb.background " +
//            "JOIN FETCH m.selectedItem si " +
//            "JOIN FETCH si.item " +
            "WHERE m.email = :email AND m.password = :password")
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);

}
