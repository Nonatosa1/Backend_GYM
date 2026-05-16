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
@Schema(description = "Datos de un tipo de clase devueltos por la API")
public class TipoClaseResponseDTO {

    @Schema(description = "Identificador unico del tipo de clase", example = "1")
    private Byte idTipoClase;

    @Schema(description = "Formato de la clase", example = "Grupal")
    private String tipoClase;

    @Schema(description = "Descripcion del tipo de clase", example = "Clase para mas de cinco personas")
    private String descripcion;

    @Schema(description = "Indica si el tipo de clase esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
