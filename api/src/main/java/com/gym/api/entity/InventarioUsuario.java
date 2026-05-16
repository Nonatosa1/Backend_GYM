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
 * Entidad que representa la asignacion de un equipo del inventario a un
 * usuario durante un periodo de tiempo. Mapea la tabla Tbl_Inventario_Usuario.
 *
 * Esta es una tabla de cruce o "tabla puente" que resuelve la relacion de
 * muchos a muchos entre Inventario y Usuario: un equipo puede haber sido
 * asignado a varios usuarios a lo largo del tiempo, y un usuario puede
 * tener asignados varios equipos simultaneamente. Cada fila documenta
 * una asignacion individual con su periodo de vigencia.
 *
 * IMPORTANTE: esta entidad modela asignaciones de uso o responsabilidad,
 * no rentas comerciales. Para esos casos transaccionales con costos
 * asociados, se utiliza Tbl_Renta_Servicios. La distincion conceptual
 * es importante para que las consultas operativas no se mezclen.
 *
 * Ejemplos de uso: registrar que un casillero pertenece a un usuario
 * por su periodo de membresia, o que un equipo esta bajo la
 * responsabilidad de un entrenador determinado.
 */
@Entity
@Table(name = "Tbl_Inventario_Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventarioUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_inventario_usuario")
    private Integer idInventarioUsuario;

    /**
     * Usuario al que se asigna el equipo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Equipo de inventario que se esta asignando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_inventario", referencedColumnName = "Id_inventario", nullable = false)
    private Inventario inventario;

    /**
     * Momento de inicio de la asignacion.
     */
    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Momento de fin previsto para la asignacion.
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
