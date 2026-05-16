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
 * DTO para crear o actualizar un pago de servicios.
 *
 * Este DTO representa el cobro efectuado por una renta de servicios.
 * Una sola renta puede tener varios pagos asociados si el cliente paga
 * en abonos, y cada pago puede usar un metodo distinto (efectivo,
 * tarjeta, transferencia, etc.).
 *
 * Las validaciones del monto siguen el patron que ya conoces de la
 * entidad Membresia: @DecimalMin para garantizar que el monto sea
 * positivo y @Digits para limitar la precision decimal a lo que la
 * base de datos puede almacenar. La diferencia respecto a Membresia
 * es que aqui exigimos un valor estrictamente mayor a cero
 * (inclusive=false), porque un pago de cero pesos no es semanticamente
 * un pago, es ruido en la base de datos.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un pago de servicios")
public class PagoServiciosRequestDTO {

    @NotNull(message = "El identificador de la renta de servicios es obligatorio")
    @Schema(description = "Identificador de la renta de servicios que se esta pagando", example = "1")
    private Integer idRentaServicio;

    @NotNull(message = "El monto total del pago es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto del pago debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2, message = "El monto debe tener maximo 8 digitos enteros y 2 decimales")
    @Schema(description = "Monto total del pago en moneda local", example = "150.00")
    private BigDecimal total;

    @NotNull(message = "El identificador del metodo de pago es obligatorio")
    @Schema(description = "Identificador del metodo de pago utilizado", example = "1")
    private Byte idMetodoPago;
}
