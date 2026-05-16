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
@Schema(description = "Datos para crear o actualizar un dia de la semana")
public class DiaRequestDTO {

    @NotBlank(message = "El nombre del dia es obligatorio")
    @Size(max = 10, message = "El nombre del dia no puede exceder los 10 caracteres")
    @Schema(description = "Nombre del dia de la semana", example = "Lunes", maxLength = 10)
    private String dia;
}
