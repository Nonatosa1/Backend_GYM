package com.gym.api.repository;

import com.gym.api.entity.RentaServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentaServiciosRepository extends JpaRepository<RentaServicios, Integer> {
}
