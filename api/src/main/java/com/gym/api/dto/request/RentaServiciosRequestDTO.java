package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar una renta de servicios.
 *
 * Representa una transaccion comercial donde un usuario renta un equipo
 * del inventario durante un periodo definido. A diferencia de
 * InventarioUsuario que modela asignaciones de responsabilidad sin
 * implicacion comercial, esta entidad si implica un cobro: cada renta
 * tiene asociado uno o varios pagos que se gestionan mediante el modulo
 * PagoServicios.
 *
 * Notese que el segundo campo de fecha se llama "fechaVence" en lugar de
 * "fechaFin". Esta nomenclatura refleja la semantica del dominio: marca
 * el momento limite hasta el cual la renta es valida contractualmente.
 * El usuario podria devolver el equipo antes de esa fecha, o el sistema
 * podria marcar la renta como vencida si se pasa de ese momento.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una renta de servicios")
public class RentaServiciosRequestDTO {

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario que renta el equipo", example = "1")
    private Integer idUsuario;

    @NotNull(message = "El identificador del inventario es obligatorio")
    @Schema(description = "Identificador del equipo de inventario rentado", example = "1")
    private Integer idInventario;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Momento en que comienza la renta", example = "2026-05-12T10:00:00")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Schema(description = "Momento limite hasta el cual la renta es valida", example = "2026-05-12T12:00:00")
    private LocalDateTime fechaVence;
}
