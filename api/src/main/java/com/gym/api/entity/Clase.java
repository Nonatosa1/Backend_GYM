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

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad que representa una clase ofrecida en el gimnasio.
 * Mapea la tabla Tbl_Clases del diagrama.
 *
 * Una clase es, en cierto modo, una plantilla recurrente que define que
 * actividad se imparte, quien la imparte, en que horario, con que cupo
 * maximo y bajo que formato. La programacion semanal de los dias en que
 * se imparte se modela aparte en Tbl_Clase_Dia, y las asistencias
 * individuales se modelan en Tbl_Asistencia.
 *
 * Tiene tres relaciones @ManyToOne: hacia Persona (el instructor
 * responsable), hacia TipoClase (formato: grupal, individual, etc.) y
 * hacia Actividad (yoga, spinning, crossfit, etc.).
 *
 * Notese que la relacion con el instructor apunta a Persona y no a
 * Usuario. Esto es una decision de diseyo que permite designar como
 * responsable a alguien que no necesariamente tiene cuenta de acceso al
 * sistema, lo cual es util para registrar instructores externos o
 * invitados que no requieren credenciales propias.
 *
 * Los campos Hora_inicio y Hora_termino son de tipo Time en MySQL, que
 * representa solo una hora del dia sin asociarla a una fecha especifica.
 * En Java se mapean a LocalTime, parte del paquete moderno java.time.
 */
@Entity
@Table(name = "Tbl_Clases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_clase")
    private Integer idClase;

    /**
     * Persona responsable de impartir la clase. Apunta a Persona y no
     * a Usuario para permitir instructores sin cuenta de sistema.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_persona_responsable", referencedColumnName = "Id_persona", nullable = false)
    private Persona personaResponsable;

    /**
     * Numero maximo de alumnos que pueden inscribirse a la clase.
     * Tipo Byte porque el diagrama declara Tinyint, suficiente para
     * cupos realistas de hasta 255 alumnos.
     */
    @Column(name = "No_max_alumnos", nullable = false)
    private Byte noMaxAlumnos;

    /**
     * Hora del dia en que comienza la clase, sin asociacion a fecha
     * especifica porque el horario es recurrente.
     */
    @Column(name = "Hora_inicio", nullable = false)
    private LocalTime horaInicio;

    /**
     * Hora del dia en que termina la clase.
     */
    @Column(name = "Hora_termino", nullable = false)
    private LocalTime horaTermino;

    /**
     * Tipo o formato de la clase (grupal, individual, presencial, etc.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_tipo_clase", referencedColumnName = "Id_tipo_clase", nullable = false)
    private TipoClase tipoClase;

    /**
     * Actividad fisica que se practica en la clase (yoga, spinning, etc.).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_actividad", referencedColumnName = "Id_actividad", nullable = false)
    private Actividad actividad;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
