package com.wargame.repository;

import com.wargame.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("select l from Log l where l.owner.id =:id")
    List<Log> findAllById(@Param("id") Long id);
}
