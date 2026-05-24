package com.gym.api.repository;

import com.gym.api.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    boolean existsByCorreo(String correo);

    Optional<Persona> findByCorreo(String correo);

    Optional<Persona> findByTokenConfirmacion(String tokenConfirmacion);

    List<Persona> findByNombreContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCase(
            String nombre, String primerApellido, String segundoApellido);
}
