package com.gym.api.repository;

import com.gym.api.dto.HistorialPagoDTO;
import com.gym.api.dto.TransaccionDTO;
import com.gym.api.entity.PagoServicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoServiciosRepository extends JpaRepository<PagoServicios, Integer> {
    /**
     * Total pagado por renta de servicios en los últimos 12 meses.
     */
    @Query("""
           SELECT COALESCE(SUM(ps.total), 0)
           FROM PagoServicios ps
           JOIN ps.rentaServicios rs
           WHERE rs.usuario.idUsuario = :idUsuario
             AND ps.habilitado = true
             AND ps.fechaAlta >= :fechaInicio
           """)
    BigDecimal totalPagadoServiciosUltimos12Meses(
            @Param("idUsuario") Integer idUsuario,
            @Param("fechaInicio") LocalDateTime fechaInicio);

    /**
     * Total de movimientos de pagos de servicios.
     */
    @Query("""
           SELECT COUNT(ps)
           FROM PagoServicios ps
           JOIN ps.rentaServicios rs
           WHERE rs.usuario.idUsuario = :idUsuario
             AND ps.habilitado = true
           """)
    Long totalMovimientosServicios(@Param("idUsuario") Integer idUsuario);

    /**
     * Historial de pagos de servicios.
     * Nota: Tbl_Pago_Servicios usa Id_tipo_pago (Tinyint) — asumo que referencia Cat_Metodo_Pago.
     */
    @Query("""
       SELECT new com.gym.api.dto.HistorialPagoDTO(
           ps.fechaAlta,
           CONCAT('Renta de equipo: ', inv.inventario),
           mp.metodoPago,
           ps.total,
           CAST('PAGADO' AS string),
           CAST(NULL AS integer)
       )
       FROM PagoServicios ps
       JOIN ps.rentaServicios rs
       JOIN rs.inventario inv
       JOIN ps.metodoPago mp
       WHERE rs.usuario.idUsuario = :idUsuario
         AND ps.habilitado = true
       ORDER BY ps.fechaAlta DESC
       """)
    List<HistorialPagoDTO> historialServicios(@Param("idUsuario") Integer idUsuario);

    /**
     * Lista de transacciones de renta de servicios en un rango de fechas.
     */
    @Query("""
       SELECT new com.gym.api.dto.TransaccionDTO(
           ps.idPagoServicio,
           ps.fechaAlta,
           CONCAT(per.nombre, ' ', per.primerApellido,
                  CASE WHEN per.segundoApellido IS NOT NULL
                       THEN CONCAT(' ', per.segundoApellido)
                       ELSE '' END),
           CONCAT('Renta de equipo: ', inv.inventario),
           ps.total
       )
       FROM PagoServicios ps
       JOIN ps.rentaServicios rs
       JOIN rs.inventario inv
       JOIN rs.usuario u
       JOIN u.persona per
       WHERE ps.habilitado = true
         AND ps.fechaAlta >= :fechaInicio
         AND ps.fechaAlta <= :fechaFin
       """)
    List<TransaccionDTO> transaccionesServiciosEnRango(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);
}
