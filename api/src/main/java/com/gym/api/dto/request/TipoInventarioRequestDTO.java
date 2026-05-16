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
@Schema(description = "Datos para crear o actualizar un tipo de inventario")
public class TipoInventarioRequestDTO {

    @NotBlank(message = "El tipo de equipo es obligatorio")
    @Size(max = 200, message = "El tipo de equipo no puede exceder los 200 caracteres")
    @Schema(description = "Categoria del equipo de inventario", example = "Equipamiento de cardio", maxLength = 200)
    private String tipoEquipo;
}
