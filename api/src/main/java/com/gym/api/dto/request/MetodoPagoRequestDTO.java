package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar un metodo de pago.
 *
 * A diferencia de catalogos mas simples, este DTO incluye un campo
 * opcional "descripcion" que permite documentar detalles del metodo.
 * Notese que descripcion no tiene @NotBlank porque puede venir null o
 * vacio, solo @Size para limitar su longitud cuando se proporciona.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un metodo de pago")
public class MetodoPagoRequestDTO {

    @NotBlank(message = "El nombre del metodo de pago es obligatorio")
    @Size(max = 100, message = "El nombre del metodo de pago no puede exceder los 100 caracteres")
    @Schema(description = "Nombre del metodo de pago", example = "Tarjeta de credito", maxLength = 100)
    private String metodoPago;

    @Size(max = 200, message = "La descripcion no puede exceder los 200 caracteres")
    @Schema(description = "Descripcion opcional del metodo de pago", example = "Visa, Mastercard y American Express", maxLength = 200)
    private String descripcion;
}
