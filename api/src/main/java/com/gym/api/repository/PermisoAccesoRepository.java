package com.gym.api.repository;

import com.gym.api.entity.PermisoAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoAccesoRepository extends JpaRepository<PermisoAcceso, Integer> {
}
