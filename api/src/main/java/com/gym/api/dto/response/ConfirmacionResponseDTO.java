package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO devuelto al confirmar una cuenta.
 *
 * Es una respuesta simple que indica el resultado de la operacion.
 * Cuando el frontend este implementado, este endpoint puede cambiar a
 * una redireccion HTTP hacia una pagina del frontend con el resultado.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta de la operacion de confirmacion de cuenta")
public class ConfirmacionResponseDTO {

    @Schema(description = "Indica si la confirmacion fue exitosa", example = "true")
    private Boolean exito;

    @Schema(description = "Mensaje descriptivo del resultado",
            example = "Tu cuenta ha sido confirmada exitosamente")
    private String mensaje;

    @Schema(description = "Nombre de la persona confirmada", example = "Juan Garcia Lopez")
    private String nombre;
}
