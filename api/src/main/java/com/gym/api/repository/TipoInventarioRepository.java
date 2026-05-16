package com.gym.api.repository;

import com.gym.api.entity.TipoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoInventarioRepository extends JpaRepository<TipoInventario, Byte> {
}
