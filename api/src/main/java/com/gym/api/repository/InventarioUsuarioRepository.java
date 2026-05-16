package com.gym.api.repository;

import com.gym.api.entity.InventarioUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioUsuarioRepository extends JpaRepository<InventarioUsuario, Integer> {
}
