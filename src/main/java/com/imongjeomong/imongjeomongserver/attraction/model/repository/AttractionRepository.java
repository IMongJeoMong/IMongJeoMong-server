package com.imongjeomong.imongjeomongserver.attraction.model.repository;

import com.imongjeomong.imongjeomongserver.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction,Long> {

    @Query("SELECT a, SQRT(POW(a.lat - :lat , 2) + POW(a.lng - :lng , 2)) AS distance FROM Attraction a " +
            "WHERE a.name LIKE CONCAT('%', :keyword, '%' ) " +
            "ORDER BY distance asc ")
    Page<Attraction> findAttractionByCondition(@Param("keyword") String keyword,
//                                               @Param("lat") double lat,
                                               @Param("lng") double lng,
                                               Pageable pageable);
}
