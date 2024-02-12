package com.wargame.repository;

import com.wargame.domain.Army;
import com.wargame.domain.Units;
import com.wargame.dto.outgoing.ArmyListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {

    @Query("select a from Army a order by a.owner.id")
    List<Army> findAllOrderedbyOwner();

    @Query("select a from Army a where a.owner.id =:id and a.type =:id2")
    Army findByOwnerAndType(@Param("id") Long id, @Param("id2") Units id2);

    @Query("select a from Army a where a.type =:id")
    Army findByType(@Param("id") Units id);

    @Query("select a from Army a where a.owner.id =:id order by a.type")
    List<Army> findAllById(@Param("id") Long id);

    @Query("select a from Army a where a.id =:id")
    Army findOneById(@Param("id") Long id);
}
