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
 * DTO que representa los datos de un elemento de inventario devueltos por
 * la API.
 *
 * Incluye informacion anidada tanto del area como del tipo de inventario,
 * siguiendo la decision de diseño de exponer los datos relacionados
 * completos en las respuestas para evitar que el cliente tenga que hacer
 * peticiones adicionales.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un elemento de inventario devueltos por la API")
public class InventarioResponseDTO {

    @Schema(description = "Identificador unico del elemento de inventario", example = "1")
    private Integer idInventario;

    @Schema(description = "Nombre del equipo", example = "Caminadora 1")
    private String inventario;

    @Schema(description = "Area donde se ubica el equipo")
    private AreaResponseDTO area;

    @Schema(description = "Tipo o categoria del equipo")
    private TipoInventarioResponseDTO tipoInventario;

    @Schema(description = "Indica si el equipo esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
