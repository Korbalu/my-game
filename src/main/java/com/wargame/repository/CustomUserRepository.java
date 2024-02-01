package com.wargame.repository;

import com.wargame.domain.Army;
import com.wargame.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findAllByEmail(String email);
}
