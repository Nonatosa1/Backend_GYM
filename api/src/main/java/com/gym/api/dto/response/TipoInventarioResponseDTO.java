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
@Schema(description = "Datos de un tipo de inventario devueltos por la API")
public class TipoInventarioResponseDTO {

    @Schema(description = "Identificador unico del tipo de inventario", example = "1")
    private Byte idTipoInventario;

    @Schema(description = "Categoria del equipo", example = "Equipamiento de cardio")
    private String tipoEquipo;

    @Schema(description = "Indica si el tipo de inventario esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
