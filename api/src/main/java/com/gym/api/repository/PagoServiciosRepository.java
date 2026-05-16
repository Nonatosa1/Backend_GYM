package com.gym.api.repository;

import com.gym.api.entity.PagoServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoServiciosRepository extends JpaRepository<PagoServicios, Integer> {
}
