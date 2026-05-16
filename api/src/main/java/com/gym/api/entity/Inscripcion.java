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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa la inscripcion de un usuario a una membresia
 * durante un periodo de tiempo. Mapea la tabla Tbl_Inscripciones del
 * diagrama.
 *
 * Una inscripcion es el momento en que un usuario adquiere el derecho a
 * usar el gimnasio bajo un tipo de membresia especifico, desde una fecha
 * inicial hasta una fecha de vencimiento. Es el evento que dispara el
 * ciclo financiero: cada inscripcion genera un pago asociado en la tabla
 * Tbl_Pagos.
 *
 * Las fechas de inicio y vencimiento son de tipo Date (sin hora) porque
 * las inscripciones se manejan a nivel de dia completo: la membresia
 * vence el dia X, no a una hora especifica de ese dia.
 *
 * Tiene dos relaciones @ManyToOne: hacia Membresia (que tipo de
 * inscripcion) y hacia Usuario (quien se inscribe).
 */
@Entity
@Table(name = "Tbl_Inscripciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_inscripcion")
    private Integer idInscripcion;

    /**
     * Membresia a la que se inscribe el usuario. Define el alcance de
     * acceso, precio y duracion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_membresia", referencedColumnName = "Id_membresia", nullable = false)
    private Membresia membresia;

    /**
     * Usuario que se inscribe.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Fecha de inicio de la vigencia de la inscripcion.
     */
    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha en la que vence la inscripcion. Despues de esta fecha el
     * usuario pierde los beneficios de la membresia.
     */
    @Column(name = "Fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
