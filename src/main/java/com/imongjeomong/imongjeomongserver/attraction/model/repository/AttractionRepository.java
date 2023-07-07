package com.imongjeomong.imongjeomongserver.attraction.model.repository;

import com.imongjeomong.imongjeomongserver.dto.AttractionDTO;
import com.imongjeomong.imongjeomongserver.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction,Long> {

    @Query(value = "SELECT new com.imongjeomong.imongjeomongserver.dto.AttractionDTO(" +
            "a.id, a.name, a.address, a.lat, a.lng, a.description, a.contentTypeId, a.imagePath, " +
            "a.tel, a.sidoCode, a.gold, a.exp, " +
            "(6371 * 2 * ASIN(SQRT(POWER(SIN((:lat - a.lat) * pi()/180 / 2), 2) + " +
            "COS(:lat * pi()/180) * COS(a.lat * pi()/180) * POWER(SIN((:lng - a.lng) * pi()/180 / 2), 2)))) * 1000 AS distance )" +
            " FROM Attraction a " +
            "WHERE a.name LIKE CONCAT('%', :keyword, '%' ) " +
            "ORDER BY distance",
            countQuery = "SELECT COUNT(*) From Attraction a")
    Page<AttractionDTO> findAttractionByCondition(@Param("keyword") String keyword,
                                                  @Param("lat") Double lat,
                                                  @Param("lng") Double lng,
                                                  Pageable pageable);
}
