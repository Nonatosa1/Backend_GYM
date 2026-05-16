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
 * Entidad que registra la asistencia de un usuario a una clase en una
 * fecha especifica. Mapea la tabla Tbl_Asistencia del diagrama.
 *
 * Cada fila representa una sesion concreta a la que un usuario estaba
 * inscrito y registra si efectivamente asistio o no mediante la bandera
 * Asistio. La tabla registra tanto asistencias confirmadas como
 * ausencias, lo cual permite analiticas mas ricas sobre el comportamiento
 * de los usuarios y la efectividad de las clases.
 *
 * El campo Fecha es de tipo Date (sin hora) porque indica el dia
 * especifico de la sesion. La hora exacta esta implicita en el horario
 * configurado en la entidad Clase (campos horaInicio y horaTermino).
 */
@Entity
@Table(name = "Tbl_Asistencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_asistencia")
    private Integer idAsistencia;

    /**
     * Clase a la que se refiere esta asistencia.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_clase", referencedColumnName = "Id_clase", nullable = false)
    private Clase clase;

    /**
     * Usuario cuya asistencia se esta registrando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Fecha especifica de la sesion. Solo dia, sin hora, porque la hora
     * esta determinada por el horario fijo de la clase.
     */
    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Bandera que indica si el usuario efectivamente asistio (true) o no
     * (false). Permite registrar tanto asistencias como ausencias.
     */
    @Column(name = "Asistio", nullable = false)
    private Boolean asistio;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
