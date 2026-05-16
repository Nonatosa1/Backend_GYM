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

/**
 * Entidad que representa una reserva o cita programada en el gimnasio.
 * Mapea la tabla Tbl_Agenda del diagrama.
 *
 * Una entrada de agenda es la reserva que hace un usuario para usar un
 * espacio fisico especifico dentro de un area durante un rango de tiempo.
 * Por ejemplo, "Juan reservo la caminadora numero 3 del area de cardio
 * de 18:00 a 19:00 el viernes". El campo agenda almacena una descripcion
 * libre que el usuario puede usar para anotar el motivo o detalles de la
 * reserva.
 *
 * Esta entidad combina tres relaciones @ManyToOne: hacia Usuario (quien
 * hace la reserva), hacia Area (donde se realiza), y hacia EspacioFisico
 * (el elemento concreto que se reserva). Mantener tanto Area como
 * EspacioFisico aunque parezca redundante (ya que el espacio fisico
 * pertenece a un area) permite consultar agendas por area directamente
 * sin necesidad de pasar por el espacio fisico cada vez, lo cual mejora
 * el rendimiento de ciertas consultas.
 */
@Entity
@Table(name = "Tbl_Agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_agenda")
    private Integer idAgenda;

    /**
     * Descripcion libre de la reserva. Puede contener detalles sobre el
     * motivo de la cita, observaciones del usuario, o cualquier informacion
     * relevante. 300 caracteres deberian ser suficientes para anotaciones
     * tipicas.
     */
    @Column(name = "Agenda", nullable = false, length = 300)
    private String agenda;

    /**
     * Usuario que realizo la reserva.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Area donde se realizara la actividad agendada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_area", referencedColumnName = "Id_area", nullable = false)
    private Area area;

    /**
     * Espacio fisico concreto dentro del area que se esta reservando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_espacio_fisico", referencedColumnName = "Id_espacio_fisico", nullable = false)
    private EspacioFisico espacioFisico;

    /**
     * Momento de inicio de la reserva, con precision horaria.
     */
    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Momento de fin de la reserva, con precision horaria.
     */
    @Column(name = "Fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
