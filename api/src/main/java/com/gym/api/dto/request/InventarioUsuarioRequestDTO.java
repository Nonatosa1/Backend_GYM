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
 * DTO para crear o actualizar una asignacion de inventario a usuario.
 *
 * Representa la asignacion de un equipo del inventario a un usuario
 * durante un periodo de tiempo, sin implicar transaccion comercial. Por
 * ejemplo, "el casillero numero 5 esta asignado a Juan durante su
 * membresia" o "el equipo de musica esta bajo la responsabilidad de la
 * entrenadora Maria".
 *
 * Para casos de renta comercial puntual con cobro asociado, debe usarse
 * el modulo RentaServicios en lugar de este.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una asignacion de inventario a usuario")
public class InventarioUsuarioRequestDTO {

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario al que se asigna el equipo", example = "1")
    private Integer idUsuario;

    @NotNull(message = "El identificador del inventario es obligatorio")
    @Schema(description = "Identificador del equipo de inventario asignado", example = "1")
    private Integer idInventario;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Momento de inicio de la asignacion", example = "2026-05-12T08:00:00")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Schema(description = "Momento de fin previsto para la asignacion", example = "2026-06-12T20:00:00")
    private LocalDateTime fechaFin;
}
