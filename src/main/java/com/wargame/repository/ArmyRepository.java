package com.wargame.repository;

import com.wargame.domain.Army;
import com.wargame.dto.outgoing.ArmyListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {

    @Query("select a from Army a order by a.owner")
    List<Army> findAllOrderedbyOwner();

    @Query("select a from Army a where a.owner =:id order by a.type")
    List<Army> findAllById(@Param("id") Long id);
    @Query("select a from Army a where a.id =:id")
    Army findOneById(@Param("id") Long id);
}
