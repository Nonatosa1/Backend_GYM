package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar un feedback de clase.
 *
 * Representa la opinion que un usuario deja sobre una clase a la que
 * asistio. El comentario es opcional para acomodar casos donde el usuario
 * quiera registrar su participacion sin necesariamente escribir texto.
 * Esta flexibilidad permite que el modulo se preste para futuras
 * extensiones como agregar calificaciones numericas sin texto asociado.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un feedback sobre una clase")
public class FeedbackRequestDTO {

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario que da el feedback", example = "1")
    private Integer idUsuario;

    @NotNull(message = "El identificador de la clase es obligatorio")
    @Schema(description = "Identificador de la clase sobre la que se da el feedback", example = "1")
    private Integer idClase;

    @Size(max = 300, message = "El comentario no puede exceder los 300 caracteres")
    @Schema(description = "Comentario opcional del usuario sobre la clase", example = "Excelente sesion, el instructor explico muy bien", maxLength = 300)
    private String comentario;
}
