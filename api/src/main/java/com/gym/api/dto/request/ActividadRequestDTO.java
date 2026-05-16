package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar una actividad fisica del gimnasio
 * (yoga, spinning, crossfit, etc.).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una actividad")
public class ActividadRequestDTO {

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    @Size(max = 150, message = "El nombre de la actividad no puede exceder los 150 caracteres")
    @Schema(description = "Nombre de la actividad", example = "Yoga", maxLength = 150)
    private String actividad;
}
