package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una asociacion clase-membresia devueltos por la API")
public class ClaseMembresiaResponseDTO {

    @Schema(description = "Identificador unico de la asociacion", example = "1")
    private Integer idClaseMembresia;

    @Schema(description = "Clase incluida en la membresia")
    private ClaseResponseDTO clase;

    @Schema(description = "Membresia que incluye la clase")
    private MembresiaResponseDTO membresia;

    @Schema(description = "Indica si la asociacion esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
