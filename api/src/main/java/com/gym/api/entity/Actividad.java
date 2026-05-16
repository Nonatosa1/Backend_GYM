package com.gym.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa las actividades fisicas ofrecidas en el gimnasio.
 * Mapea la tabla Cat_actividades del diagrama.
 *
 * Ejemplos tipicos: yoga, spinning, crossfit, pesas, zumba. La tabla
 * Tbl_Clases referencia este catalogo para indicar que tipo de actividad
 * se imparte en cada clase programada.
 */
@Entity
@Table(name = "Cat_actividades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_actividad")
    private Byte idActividad;

    @Column(name = "Actividad", nullable = false, length = 150)
    private String actividad;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
