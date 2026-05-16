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
 * Entidad que representa los tipos o categorias de inventario del gimnasio.
 * Mapea la tabla Cat_tipo_inventario del diagrama.
 *
 * Ejemplos tipicos: equipamiento de cardio, equipamiento de fuerza,
 * accesorios, equipamiento auxiliar. La tabla Tbl_Inventario referencia
 * este catalogo para clasificar cada equipo registrado en el sistema.
 */
@Entity
@Table(name = "Cat_tipo_inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_tipo_inventario")
    private Byte idTipoInventario;

    @Column(name = "Tipo_equipo", nullable = false, length = 200)
    private String tipoEquipo;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
