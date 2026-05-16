package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO que representa los datos de una asignacion de inventario a usuario
 * devueltos por la API.
 *
 * Como en otros modulos del proyecto, incluye informacion anidada tanto
 * del usuario como del inventario para que el cliente reciba en una sola
 * peticion todos los datos relevantes. Esto es particularmente util en
 * vistas administrativas donde se quiere consultar rapidamente quien
 * tiene asignado que equipo sin hacer multiples peticiones.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una asignacion de inventario a usuario")
public class InventarioUsuarioResponseDTO {

    @Schema(description = "Identificador unico de la asignacion", example = "1")
    private Integer idInventarioUsuario;

    @Schema(description = "Usuario al que se asigna el equipo")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Equipo de inventario asignado")
    private InventarioResponseDTO inventario;

    @Schema(description = "Momento de inicio de la asignacion", example = "2026-05-12T08:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Momento de fin de la asignacion", example = "2026-06-12T20:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Indica si la asignacion esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
