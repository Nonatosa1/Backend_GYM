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
 * Entidad que representa las areas fisicas del gimnasio.
 * Mapea la tabla Cat_areas del diagrama.
 *
 * Ejemplos tipicos: sala de pesas, area de cardio, alberca, salon de
 * clases grupales, vestidores. Es una de las entidades mas referenciadas
 * del sistema: Tbl_Espacios_Fisicos asocia espacios concretos a un area,
 * Tbl_Inventario indica en que area se encuentra cada equipo, y
 * Tbl_Permisos_Acceso controla a que areas puede acceder cada usuario.
 *
 * Notar que el tipo de Id_area es Int segun el diagrama, no Tinyint
 * como en otros catalogos. Esto sugiere que se preven mas areas posibles
 * o que el diseyo original previo escalabilidad mayor para esta tabla.
 */
@Entity
@Table(name = "Cat_areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_area")
    private Integer idArea;

    @Column(name = "Area", nullable = false, length = 150)
    private String area;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
