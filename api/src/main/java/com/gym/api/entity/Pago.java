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
 * Entidad que representa el cobro total asociado a una inscripcion.
 * Mapea la tabla Tbl_Pagos del diagrama.
 *
 * Cada inscripcion genera un pago con un monto total que el usuario debe
 * cubrir. Sin embargo, ese pago no necesariamente se efectua en una sola
 * transaccion: puede dividirse en multiples abonos a lo largo del tiempo,
 * cada uno registrado como un detalle individual en Tbl_Detalles_Pagos.
 *
 * Esta estructura permite manejar tanto pagos unicos (un solo
 * DetallePago que cubre el monto total) como pagos diferidos
 * (multiples DetallesPagos que suman el monto total).
 *
 * El monto total se modela con BigDecimal por las razones que ya
 * conocemos: los tipos de punto flotante introducen errores de redondeo
 * inaceptables en calculos financieros.
 *
 * Notese que esta entidad NO declara una coleccion @OneToMany hacia
 * DetallePago. Cuando necesitemos obtener todos los detalles de un pago,
 * lo haremos explicitamente a traves del repositorio de DetallePago.
 * Esta decision deliberada mantiene el modelo simple y previene
 * problemas comunes de las relaciones bidireccionales.
 */
@Entity
@Table(name = "Tbl_Pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_pago")
    private Integer idPago;

    /**
     * Inscripcion a la que esta asociado este pago.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Inscripcion", referencedColumnName = "Id_inscripcion", nullable = false)
    private Inscripcion inscripcion;

    /**
     * Monto total que debe ser cubierto por el conjunto de abonos
     * (detalles de pago) asociados a este pago.
     */
    @Column(name = "Monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
