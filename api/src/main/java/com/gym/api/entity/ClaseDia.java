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
 * Entidad que representa la programacion semanal de una clase en un dia
 * especifico. Mapea la tabla Tbl_Clase_Dia del diagrama.
 *
 * Esta es una tabla puente que resuelve la relacion de muchos a muchos
 * entre Clase y Dia: una misma clase puede impartirse en varios dias de
 * la semana (por ejemplo, yoga los lunes, miercoles y viernes), y un
 * mismo dia tiene varias clases programadas. Cada fila representa una
 * combinacion clase-dia especifica.
 *
 * Modelarla como entidad explicita en lugar de usar @ManyToMany sigue
 * la misma logica que con InventarioUsuario: la tabla puente tiene sus
 * propios campos de auditoria y de habilitacion, lo que la convierte en
 * una entidad con identidad propia mas alla de simplemente conectar dos
 * tablas.
 */
@Entity
@Table(name = "Tbl_Clase_Dia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_clase_dia")
    private Integer idClaseDia;

    /**
     * Clase que se imparte en el dia especificado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_clase", referencedColumnName = "Id_clase", nullable = false)
    private Clase clase;

    /**
     * Dia de la semana en que se imparte la clase.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_dia", referencedColumnName = "Id_dia", nullable = false)
    private Dia dia;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
