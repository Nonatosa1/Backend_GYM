package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO que representa los datos de una asociacion clase-dia devueltos por
 * la API.
 *
 * Como el DTO de peticion era minimalista, este DTO de respuesta tambien
 * es relativamente simple. Sin embargo, incluimos los objetos anidados
 * completos de Clase y Dia porque eso es lo que da valor real al
 * registro: ver que "la clase de yoga (con todos sus detalles) se imparte
 * los lunes" es mucho mas util que ver "el registro 5 conecta el id 3 con
 * el id 1".
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una asociacion clase-dia devueltos por la API")
public class ClaseDiaResponseDTO {

    @Schema(description = "Identificador unico de la asociacion", example = "1")
    private Integer idClaseDia;

    @Schema(description = "Clase asociada al dia")
    private ClaseResponseDTO clase;

    @Schema(description = "Dia de la semana en que se imparte la clase")
    private DiaResponseDTO dia;

    @Schema(description = "Indica si la asociacion esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
