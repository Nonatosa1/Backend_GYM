package com.gym.api.repository;

import com.gym.api.entity.TipoClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoClaseRepository extends JpaRepository<TipoClase, Byte> {
}
