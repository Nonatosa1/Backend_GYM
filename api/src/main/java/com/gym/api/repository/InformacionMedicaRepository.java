package com.gym.api.repository;

import com.gym.api.entity.InformacionMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacionMedicaRepository extends JpaRepository<InformacionMedica, Integer> {
}
