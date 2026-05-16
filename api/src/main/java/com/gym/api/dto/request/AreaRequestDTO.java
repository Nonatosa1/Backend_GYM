package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un area del gimnasio")
public class AreaRequestDTO {

    @NotBlank(message = "El nombre del area es obligatorio")
    @Size(max = 150, message = "El nombre del area no puede exceder los 150 caracteres")
    @Schema(description = "Nombre del area", example = "Area de cardio", maxLength = 150)
    private String area;
}
