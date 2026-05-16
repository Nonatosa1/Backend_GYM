package com.gym.api.repository;

import com.gym.api.entity.ClaseMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseMembresiaRepository extends JpaRepository<ClaseMembresia, Integer> {
}
