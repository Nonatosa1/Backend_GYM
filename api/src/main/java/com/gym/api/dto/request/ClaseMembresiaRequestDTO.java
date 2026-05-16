package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar una asociacion entre clase y tipo de membresia.
 *
 * Esta tabla puente expresa que membresias incluyen acceso a que clases.
 * Por ejemplo, la membresia Premium podria incluir acceso a las clases
 * de yoga, spinning y crossfit, lo cual requeriria tres registros de
 * ClaseMembresia.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para asociar una clase con una membresia")
public class ClaseMembresiaRequestDTO {

    @NotNull(message = "El identificador de la clase es obligatorio")
    @Schema(description = "Identificador de la clase incluida", example = "1")
    private Integer idClase;

    @NotNull(message = "El identificador de la membresia es obligatorio")
    @Schema(description = "Identificador de la membresia que incluye la clase", example = "1")
    private Short idMembresia;
}
