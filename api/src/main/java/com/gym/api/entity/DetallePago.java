package com.gym.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que registra un abono o pago parcial efectuado contra un Pago.
 * Mapea la tabla Tbl_Detalles_Pagos del diagrama.
 *
 * Cada vez que el usuario efectua un abono contra su pago de inscripcion,
 * se crea un registro de DetallePago que documenta el monto abonado, la
 * fecha del abono y el metodo de pago utilizado. Un pago puede tener
 * uno o varios detalles asociados:
 *
 * - Pago unico: un solo DetallePago que cubre el monto total
 * - Pago diferido: multiples DetallesPagos que en suma cubren el total
 *
 * Tiene dos relaciones @ManyToOne: hacia Pago (a que pago pertenece el
 * abono) y hacia MetodoPago (como se efectuo este abono especifico).
 * Esto significa que distintos abonos del mismo pago pueden hacerse con
 * metodos diferentes, lo cual aporta flexibilidad para el usuario.
 *
 * El monto del abono se modela con BigDecimal por la misma razon que el
 * monto total en Pago: precision exacta para valores monetarios.
 */
@Entity
@Table(name = "Tbl_Detalles_Pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_detalle_pago")
    private Integer idDetallePago;

    /**
     * Pago al que pertenece este abono.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_pago", referencedColumnName = "Id_pago", nullable = false)
    private Pago pago;

    /**
     * Monto del abono particular. La suma de todos los abonos de un mismo
     * pago debe igualar (o cubrir) el monto total declarado en el pago.
     */
    @Column(name = "Monto_abono", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoAbono;

    /**
     * Momento en que se efectuo el abono. El diagrama original tenia
     * inconsistencias en el tipo de este campo; modelarlo como
     * LocalDateTime es lo coherente con el dominio (registrar el momento
     * exacto del abono).
     */
    @Column(name = "Fecha_abono", nullable = false)
    private LocalDateTime fechaAbono;

    /**
     * Metodo de pago utilizado para este abono especifico. Distintos
     * abonos del mismo pago pueden hacerse con distintos metodos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_metodo_pago", referencedColumnName = "Id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
