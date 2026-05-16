package com.gym.api.repository;

import com.gym.api.entity.ClaseDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseDiaRepository extends JpaRepository<ClaseDia, Integer> {
}
