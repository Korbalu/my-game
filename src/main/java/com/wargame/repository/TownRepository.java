package com.wargame.repository;

import com.wargame.domain.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {
    Town findByOwnerId(Long id);

    @Query("select t from Town t where t.owner.id=:id")
    Optional<Town> findByOwner(@Param("id") Long id);
}
