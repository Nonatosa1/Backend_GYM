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
 * Entidad que representa los tipos o formatos de clases del gimnasio.
 * Mapea la tabla Cat_tipos_clase del diagrama.
 *
 * Ejemplos tipicos: clase grupal, clase individual, clase presencial,
 * clase en linea. La tabla Tbl_Clases referencia este catalogo para
 * indicar el formato bajo el cual se imparte cada clase.
 */
@Entity
@Table(name = "Cat_tipos_clase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_tipo_clase")
    private Byte idTipoClase;

    @Column(name = "Tipo_clase", nullable = false, length = 100)
    private String tipoClase;

    @Column(name = "Descripcion", length = 200)
    private String descripcion;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
