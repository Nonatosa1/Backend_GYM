package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para crear o actualizar un pago de membresia.
 *
 * El pago representa el compromiso de pago total asociado a una
 * inscripcion. No representa lo que se ha pagado, sino lo que se debe
 * pagar en total. Los abonos parciales se modelan en el modulo
 * DetallePago, que apunta a este Pago. Esta separacion permite modelar
 * fielmente flujos comerciales reales con pagos en cuotas o parciales.
 *
 * Las validaciones del monto siguen el patron que ya conoces de
 * PagoServicios y Membresia: @DecimalMin para garantizar positividad
 * estricta, y @Digits para limitar la precision decimal a lo que la
 * base de datos puede almacenar.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un pago de membresia")
public class PagoRequestDTO {

    @NotNull(message = "El identificador de la inscripcion es obligatorio")
    @Schema(description = "Identificador de la inscripcion asociada al pago", example = "1")
    private Integer idInscripcion;

    @NotNull(message = "El monto total del pago es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2, message = "El monto debe tener maximo 8 digitos enteros y 2 decimales")
    @Schema(description = "Monto total comprometido para esta inscripcion", example = "899.99")
    private BigDecimal montoTotal;
}
