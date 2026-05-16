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
 * Entidad que representa la renta o servicio de un equipo del inventario
 * a un usuario. Mapea la tabla Tbl_Renta_Servicios del diagrama.
 *
 * A diferencia de Tbl_Inventario_Usuario, que modela asignaciones de uso
 * o responsabilidad sin necesariamente involucrar cobros, esta tabla
 * modela transacciones comerciales: el usuario paga por usar el equipo
 * durante un periodo. La presencia de Tbl_Pago_Servicios, que referencia
 * a esta tabla, confirma su naturaleza transaccional.
 *
 * Ejemplos de uso: renta de una toalla por una sesion, alquiler de un
 * casillero temporal, prestamo de un equipo especial.
 *
 * El campo Fecha_vence (no Fecha_fin como en otras tablas) refleja el
 * momento limite hasta el cual la renta es valida. El usuario podria
 * devolver el equipo antes de esa fecha, o el sistema podria marcar la
 * renta como vencida si se pasa de ese momento.
 */
@Entity
@Table(name = "Tbl_Renta_Servicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentaServicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_renta_servicio")
    private Integer idRentaServicio;

    /**
     * Usuario que renta el equipo o servicio.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_usuario", referencedColumnName = "Id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Equipo del inventario que se esta rentando.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_inventario", referencedColumnName = "Id_inventario", nullable = false)
    private Inventario inventario;

    /**
     * Momento en que comienza la renta.
     */
    @Column(name = "Fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /**
     * Momento limite hasta el cual la renta es valida.
     */
    @Column(name = "Fecha_vence", nullable = false)
    private LocalDateTime fechaVence;

    @Column(name = "Habilitado", nullable = false)
    private Boolean habilitado;

    @Column(name = "Fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_baja")
    private LocalDateTime fechaBaja;
}
