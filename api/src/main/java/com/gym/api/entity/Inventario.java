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
 * Entidad que representa un equipo o elemento del inventario del gimnasio.
 * Mapea la tabla Tbl_Inventario del diagrama.
 *
 * Cada fila representa un equipo concreto disponible en el gimnasio,
 * por ejemplo una caminadora especifica, una mancuerna, un balon, una
 * maquina de pesas, una toalla o cualquier otro elemento gestionado.
 * Esta entidad es la base del bloque de inventario y es referenciada
 * por las tablas que registran asignaciones (Tbl_Inventario_Usuario) y
 * rentas (Tbl_Renta_Servicios).
 *
 * Tiene dos relaciones @ManyToOne: hacia Area (donde se encuentra el
 * equipo fisicamente) y hacia TipoInventario (que clasifica el equipo
 * en su categoria, como cardio, fuerza o accesorios).
 */
@Entity
@Table(name = "Tbl_Inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_inventario")
    private Integer idInventario;

    /**
     * Nombre o descripcion corta del equipo de inventario.
     */
    @Column(name = "Inventario", nullable = false, length = 150)
    private String inventario;

    /**
     * Area fisica donde se encuentra el equipo. Determina en que zona
     * del gimnasio esta disponible.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_area", referencedColumnName = "Id_area", nullable = false)
    private Area area;

    /**
     * Categoria del equipo. Permite agrupar equipos por su tipo y
     * facilita inventarios filtrados.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_tipo_inventario", referencedColumnName = "Id_tipo_inventario", nullable = false)
    private TipoInventario tipoInventario;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
