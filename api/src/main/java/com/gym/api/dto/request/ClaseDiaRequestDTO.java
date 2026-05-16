package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar una asociacion entre clase y dia de la semana.
 *
 * Esta es la primera tabla puente pura del proyecto, con un DTO
 * minimalista que solo contiene las referencias a las dos entidades
 * conectadas. La entidad ClaseDia no tiene atributos propios mas alla de
 * los de auditoria, asi que el DTO refleja honestamente esa simplicidad.
 *
 * Conceptualmente, cada registro de este tipo dice "la clase X se imparte
 * en el dia Y de la semana". Para que la clase de yoga se imparta los
 * lunes, miercoles y viernes, deben existir tres registros de ClaseDia
 * apuntando a esa clase, uno por cada dia correspondiente.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para asociar una clase con un dia de la semana")
public class ClaseDiaRequestDTO {

    @NotNull(message = "El identificador de la clase es obligatorio")
    @Schema(description = "Identificador de la clase a programar", example = "1")
    private Integer idClase;

    @NotNull(message = "El identificador del dia es obligatorio")
    @Schema(description = "Identificador del dia de la semana", example = "1")
    private Byte idDia;
}
