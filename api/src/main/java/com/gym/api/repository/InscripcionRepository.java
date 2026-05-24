package com.gym.api.repository;

import com.gym.api.dto.MiembroActivoDTO;
import com.gym.api.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    @Query("""
           SELECT i FROM Inscripcion i
           JOIN FETCH i.membresia m
           WHERE i.usuario.idUsuario = :idUsuario
             AND i.habilitado = true
             AND i.fechaVencimiento >= :hoy
           ORDER BY i.fechaVencimiento ASC
           """)
    List<Inscripcion> proximaInscripcionPorVencer(
            @Param("idUsuario") Integer idUsuario,
            @Param("hoy") LocalDate hoy);

    /**
     * Total de miembros activos: usuarios con al menos una inscripción
     * cuya vigencia incluye la fecha actual.
     * Usamos COUNT DISTINCT por si un usuario tiene varias inscripciones activas.
     */
    @Query("""
           SELECT COUNT(DISTINCT i.usuario.idUsuario)
           FROM Inscripcion i
           WHERE i.habilitado = true
             AND i.fechaInicio <= :hoy
             AND i.fechaVencimiento >= :hoy
           """)
    Long contarMiembrosActivos(@Param("hoy") LocalDate hoy);

    /**
     * Nuevas altas en los últimos 30 días.
     * Cuenta inscripciones (no usuarios) creadas en ese rango.
     */
    @Query("""
           SELECT COUNT(i)
           FROM Inscripcion i
           WHERE i.habilitado = true
             AND i.fechaInicio >= :fechaCorte
             AND i.fechaInicio <= :hoy
           """)
    Long contarNuevasAltas(@Param("fechaCorte") LocalDate fechaCorte,
                           @Param("hoy") LocalDate hoy);

    /**
     * Membresías vencidas en los últimos 30 días.
     * Su fecha de vencimiento cayó dentro de la ventana y ya pasó.
     */
    @Query("""
           SELECT COUNT(i)
           FROM Inscripcion i
           WHERE i.habilitado = true
             AND i.fechaVencimiento >= :fechaCorte
             AND i.fechaVencimiento < :hoy
           """)
    Long contarMembresiasVencidas(@Param("fechaCorte") LocalDate fechaCorte,
                                  @Param("hoy") LocalDate hoy);

    /**
     * Listado de miembros con membresía vigente al día de hoy.
     * Asume que Usuario tiene relación con Persona, y Persona tiene
     * nombre, primerApellido, segundoApellido y correo.
     */
    @Query("""
           SELECT new com.gym.api.dto.MiembroActivoDTO(
               u.idUsuario,
               CONCAT(p.nombre, ' ', p.primerApellido,
                      CASE WHEN p.segundoApellido IS NOT NULL
                           THEN CONCAT(' ', p.segundoApellido)
                           ELSE '' END),
               p.correo,
               m.membresia,
               i.fechaInicio,
               i.fechaVencimiento,
               CAST(0 AS long)
           )
           FROM Inscripcion i
           JOIN i.usuario u
           JOIN u.persona p
           JOIN i.membresia m
           WHERE i.habilitado = true
             AND i.fechaInicio <= :hoy
             AND i.fechaVencimiento >= :hoy
           ORDER BY i.fechaVencimiento ASC
           """)
    List<MiembroActivoDTO> listarMiembrosVigentes(@Param("hoy") LocalDate hoy);
}
