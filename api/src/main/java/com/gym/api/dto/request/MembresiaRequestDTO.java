package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para crear o actualizar una membresia del gimnasio.
 *
 * A diferencia de los catalogos simples, esta entidad tiene validaciones
 * mas ricas debido a la naturaleza de sus campos. El precio requiere
 * validaciones de rango y precision decimal. La duracion requiere ser
 * positiva. Y todos los campos de negocio son obligatorios excepto la
 * descripcion, que es opcional.
 *
 * Las anotaciones de validacion usadas:
 *   @NotBlank: el campo no puede ser null ni cadena vacia ni solo espacios
 *   @NotNull: el campo no puede ser null (para tipos no String)
 *   @Size: limita la longitud para campos de tipo String
 *   @DecimalMin: valor minimo para BigDecimal y similares
 *   @Digits: limita la cantidad de digitos enteros y decimales
 *   @Min: valor minimo para tipos numericos enteros
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una membresia")
public class MembresiaRequestDTO {

    @NotBlank(message = "El nombre de la membresia es obligatorio")
    @Size(max = 100, message = "El nombre de la membresia no puede exceder los 100 caracteres")
    @Schema(description = "Nombre de la membresia", example = "Membresia Premium", maxLength = 100)
    private String membresia;

    @Size(max = 250, message = "La descripcion no puede exceder los 250 caracteres")
    @Schema(description = "Descripcion opcional de la membresia", example = "Acceso ilimitado a todas las clases y areas")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener maximo 8 digitos enteros y 2 decimales")
    @Schema(description = "Precio de la membresia en moneda local", example = "899.99")
    private BigDecimal precio;

    @NotNull(message = "La duracion en dias es obligatoria")
    @Min(value = 1, message = "La duracion debe ser de al menos un dia")
    @Schema(description = "Duracion de la membresia en dias", example = "30")
    private Short duracionDias;

    @NotNull(message = "Debe indicar si la membresia incluye clases")
    @Schema(description = "Indica si la membresia incluye acceso a clases grupales", example = "true")
    private Boolean incluyeClases;
}
