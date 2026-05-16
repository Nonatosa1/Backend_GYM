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
 * Entidad que define que clases estan incluidas en cada tipo de membresia.
 * Mapea la tabla Tbl_Clases_Membresia del diagrama.
 *
 * Esta es otra tabla puente que resuelve la relacion de muchos a muchos
 * entre Clase y Membresia: una clase puede estar incluida en varias
 * membresias (por ejemplo, una clase de yoga incluida tanto en la
 * membresia premium como en la avanzada), y una membresia incluye varias
 * clases. Sin esta tabla, definir el contenido de las membresias seria
 * rigido e imposible de mantener.
 *
 * Permite definir flexiblemente que clases pertenecen a que niveles de
 * membresia: la membresia basica podria incluir solo unas pocas clases,
 * mientras que la premium incluye todas las del catalogo.
 */
@Entity
@Table(name = "Tbl_Clases_Membresia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaseMembresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_clase_membresia")
    private Integer idClaseMembresia;

    /**
     * Clase que se incluye en la membresia.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_clase", referencedColumnName = "Id_clase", nullable = false)
    private Clase clase;

    /**
     * Membresia que incluye la clase.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_membresia", referencedColumnName = "Id_membresia", nullable = false)
    private Membresia membresia;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
