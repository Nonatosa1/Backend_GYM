package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una membresia devueltos por la API")
public class MembresiaResponseDTO {

    @Schema(description = "Identificador unico de la membresia", example = "1")
    private Short idMembresia;

    @Schema(description = "Nombre de la membresia", example = "Membresia Premium")
    private String membresia;

    @Schema(description = "Descripcion de la membresia", example = "Acceso ilimitado a todas las clases y areas")
    private String descripcion;

    @Schema(description = "Precio de la membresia en moneda local", example = "899.99")
    private BigDecimal precio;

    @Schema(description = "Duracion de la membresia en dias", example = "30")
    private Short duracionDias;

    @Schema(description = "Indica si la membresia incluye acceso a clases grupales", example = "true")
    private Boolean incluyeClases;

    @Schema(description = "Indica si la membresia esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
