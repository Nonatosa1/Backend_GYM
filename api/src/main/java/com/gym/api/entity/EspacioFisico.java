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
 * Entidad que representa una ubicacion fisica concreta dentro del gimnasio.
 * Mapea la tabla Tbl_Espacios_Fisicos del diagrama.
 *
 * Cada espacio fisico pertenece a un area especifica. Por ejemplo, una
 * caminadora particular dentro del area de cardio, un casillero dentro del
 * area de vestidores, o el ring de boxeo dentro del area de combate.
 * Esta granularidad permite agendar el uso de elementos especificos en
 * lugar de areas completas.
 *
 * El tipo de Id_espacio_fisico es Short porque el diagrama lo declara como
 * Smallint, sugiriendo un volumen moderado de espacios fisicos.
 */
@Entity
@Table(name = "Tbl_Espacios_Fisicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspacioFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_espacio_fisico")
    private Short idEspacioFisico;

    @Column(name = "Espacio_fisico", nullable = false, length = 150)
    private String espacioFisico;

    /**
     * Relacion hacia el area a la que pertenece este espacio fisico.
     * Carga perezosa para tener control sobre cuando se consulta el area.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_area", referencedColumnName = "Id_area", nullable = false)
    private Area area;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
