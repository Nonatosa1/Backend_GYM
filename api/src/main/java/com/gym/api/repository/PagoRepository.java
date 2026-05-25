package com.gym.api.repository;

import com.gym.api.dto.MiembroPorVencerDTO;
import com.gym.api.dto.TransaccionDTO;
import com.gym.api.entity.Pago;
import com.gym.api.dto.HistorialPagoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Query("""
           SELECT COALESCE(SUM(dp.montoAbono), 0)
           FROM DetallePago dp
           JOIN dp.pago p
           JOIN p.inscripcion i
           WHERE i.usuario.idUsuario = :idUsuario
             AND dp.habilitado = true
             AND dp.fechaAbono >= :fechaInicio
           """)
    BigDecimal totalPagadoInscripcionesUltimos12Meses(
            @Param("idUsuario") Integer idUsuario,
            @Param("fechaInicio") LocalDateTime fechaInicio);

    /**
     * Cuenta cuántas órdenes (Pagos) del usuario aún no están totalmente cubiertas
     * por sus abonos. Una orden está pendiente si la suma de sus abonos es menor
     * al monto total.
     */
    @Query("""
       SELECT COUNT(p)
       FROM Pago p
       JOIN p.inscripcion i
       WHERE i.usuario.idUsuario = :idUsuario
         AND p.habilitado = true
         AND p.montoTotal > COALESCE(
              (SELECT SUM(d.montoAbono) FROM DetallePago d
               WHERE d.pago = p AND d.habilitado = true), 0)
       """)
    Long contarOrdenesPendientes(@Param("idUsuario") Integer idUsuario);

    /**
     * Historial de órdenes (Pagos) que aún tienen saldo pendiente.
     * Se incluyen en el historial unificado con estado PENDIENTE o PARCIAL
     * (según si tienen al menos un abono o ninguno).
     */
    @Query("""
       SELECT new com.gym.api.dto.HistorialPagoDTO(
           p.fechaAlta,
           CONCAT('Membresía: ', m.membresia, ' (saldo pendiente)'),
           CAST('-' AS string),
           p.montoTotal - COALESCE(
               (SELECT SUM(d.montoAbono) FROM DetallePago d
                WHERE d.pago = p AND d.habilitado = true), CAST(0 AS BigDecimal)),
           CASE
               WHEN COALESCE(
                   (SELECT SUM(d.montoAbono) FROM DetallePago d
                    WHERE d.pago = p AND d.habilitado = true), CAST(0 AS BigDecimal)) = 0
                    THEN CAST('PENDIENTE' AS string)
               ELSE CAST('PARCIAL' AS string)
           END,
           p.idPago
       )
       FROM Pago p
       JOIN p.inscripcion i
       JOIN i.membresia m
       WHERE i.usuario.idUsuario = :idUsuario
         AND p.habilitado = true
         AND p.montoTotal > COALESCE(
              (SELECT SUM(d.montoAbono) FROM DetallePago d
               WHERE d.pago = p AND d.habilitado = true), CAST(0 AS BigDecimal))
       ORDER BY p.fechaAlta DESC
       """)
    List<HistorialPagoDTO> historialOrdenesPendientes(@Param("idUsuario") Integer idUsuario);

    /**
     * Suma el saldo pendiente de todas las órdenes (Pagos) del usuario que aún
     * no están totalmente cubiertas por sus abonos. Es decir, cuánto le falta
     * por aportar en total.
     */
    @Query("""
       SELECT COALESCE(SUM(p.montoTotal - COALESCE(
              (SELECT SUM(d.montoAbono) FROM DetallePago d
               WHERE d.pago = p AND d.habilitado = true), 0)), 0)
       FROM Pago p
       JOIN p.inscripcion i
       WHERE i.usuario.idUsuario = :idUsuario
         AND p.habilitado = true
         AND p.montoTotal > COALESCE(
              (SELECT SUM(d.montoAbono) FROM DetallePago d
               WHERE d.pago = p AND d.habilitado = true), 0)
       """)
    BigDecimal montoTotalPorAportar(@Param("idUsuario") Integer idUsuario);

    @Query("""
       SELECT new com.gym.api.dto.HistorialPagoDTO(
           dp.fechaAbono,
           CONCAT('Membresía: ', m.membresia),
           mp.metodoPago,
           dp.montoAbono,
           CASE
               WHEN (SELECT COALESCE(SUM(d.montoAbono), 0) FROM DetallePago d
                     WHERE d.pago = p AND d.habilitado = true) >= p.montoTotal
                    THEN CAST('PAGADO' AS string)
               ELSE CAST('PARCIAL' AS string)
           END,
           CAST(NULL AS integer)
       )
       FROM DetallePago dp
       JOIN dp.pago p
       JOIN p.inscripcion i
       JOIN i.membresia m
       JOIN dp.metodoPago mp
       WHERE i.usuario.idUsuario = :idUsuario
         AND dp.habilitado = true
       ORDER BY dp.fechaAbono DESC
       """)
    List<HistorialPagoDTO> historialInscripciones(@Param("idUsuario") Integer idUsuario);

    @Query("""
           SELECT COUNT(dp)
           FROM DetallePago dp
           JOIN dp.pago p
           JOIN p.inscripcion i
           WHERE i.usuario.idUsuario = :idUsuario
             AND dp.habilitado = true
           """)
    Long totalMovimientosInscripciones(@Param("idUsuario") Integer idUsuario);

    /**
     * Lista de transacciones de inscripciones/membresías en un rango de fechas.
     */
    @Query("""
       SELECT new com.gym.api.dto.TransaccionDTO(
           dp.idDetallePago,
           dp.fechaAbono,
           CONCAT(p.nombre, ' ', p.primerApellido,
                  CASE WHEN p.segundoApellido IS NOT NULL
                       THEN CONCAT(' ', p.segundoApellido)
                       ELSE '' END),
           CONCAT('Membresía: ', m.membresia),
           dp.montoAbono
       )
       FROM DetallePago dp
       JOIN dp.pago pago
       JOIN pago.inscripcion i
       JOIN i.membresia m
       JOIN i.usuario u
       JOIN u.persona p
       WHERE dp.habilitado = true
         AND dp.fechaAbono >= :fechaInicio
         AND dp.fechaAbono <= :fechaFin
       """)
    List<TransaccionDTO> transaccionesInscripcionesEnRango(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Listado de inscripciones que vencen en los próximos N días desde hoy.
     * Trae datos de persona y membresía. Los días para vencer y el estado
     * se calculan en el service para mantener el query portable entre BDs.
     */
    @Query("""
       SELECT new com.gym.api.dto.MiembroPorVencerDTO(
           CONCAT(p.nombre, ' ', p.primerApellido,
                  CASE WHEN p.segundoApellido IS NOT NULL
                       THEN CONCAT(' ', p.segundoApellido)
                       ELSE '' END),
           p.correo,
           m.membresia,
           i.fechaVencimiento,
           CAST(0 AS long),
           ''
       )
       FROM Inscripcion i
       JOIN i.usuario u
       JOIN u.persona p
       JOIN i.membresia m
       WHERE i.habilitado = true
         AND i.fechaVencimiento >= :hoy
         AND i.fechaVencimiento <= :fechaLimite
       ORDER BY i.fechaVencimiento ASC
       """)
    List<MiembroPorVencerDTO> listarProximosVencer(
            @Param("hoy") LocalDate hoy,
            @Param("fechaLimite") LocalDate fechaLimite);
}
