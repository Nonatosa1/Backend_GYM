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
 * DTO que representa los datos de un espacio fisico devueltos por la API.
 *
 * Incluye el AreaResponseDTO anidado en lugar de solo el idArea, siguiendo
 * la decision de diseño que tomamos en el modulo de Usuario: en las
 * respuestas exponemos la informacion completa para que el cliente no
 * tenga que hacer peticiones adicionales para mostrar datos utiles. El
 * costo de incluir el area completa es minimo porque Area es una entidad
 * pequeya.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un espacio fisico devueltos por la API")
public class EspacioFisicoResponseDTO {

    @Schema(description = "Identificador unico del espacio fisico", example = "1")
    private Short idEspacioFisico;

    @Schema(description = "Nombre del espacio fisico", example = "Caminadora 3")
    private String espacioFisico;

    @Schema(description = "Area a la que pertenece el espacio fisico")
    private AreaResponseDTO area;

    @Schema(description = "Indica si el espacio fisico esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
