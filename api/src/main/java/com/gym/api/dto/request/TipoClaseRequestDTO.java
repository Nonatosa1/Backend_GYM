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
@Schema(description = "Datos para crear o actualizar un tipo de clase")
public class TipoClaseRequestDTO {

    @NotBlank(message = "El tipo de clase es obligatorio")
    @Size(max = 100, message = "El tipo de clase no puede exceder los 100 caracteres")
    @Schema(description = "Formato de la clase", example = "Grupal", maxLength = 100)
    private String tipoClase;

    @Size(max = 200, message = "La descripcion no puede exceder los 200 caracteres")
    @Schema(description = "Descripcion opcional del tipo de clase", example = "Clase para mas de cinco personas", maxLength = 200)
    private String descripcion;
}
