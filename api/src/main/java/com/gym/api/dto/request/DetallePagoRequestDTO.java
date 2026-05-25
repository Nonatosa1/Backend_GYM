package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar un detalle de pago.
 *
 * Cada detalle de pago representa un abono concreto asociado a un Pago
 * (compromiso total). Un mismo Pago puede tener multiples
 * DetallePagos si el cliente abona en cuotas. Cada abono puede usar un
 * metodo de pago distinto, lo cual es comun cuando los clientes
 * combinan efectivo, tarjeta, transferencia, etc.
 *
 * La fechaAbono representa cuando ocurrio efectivamente el pago, que
 * puede ser anterior a la fecha de captura del registro. Por eso usamos
 * @PastOrPresent: el abono ya debio haber ocurrido (no se permiten
 * abonos futuros, lo cual seria semanticamente extraño).
 *
 * Las validaciones del monto siguen el patron habitual: positividad
 * estricta y limitacion de precision decimal.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un detalle de pago (abono)")
public class DetallePagoRequestDTO {

    @NotNull(message = "El identificador del pago es obligatorio")
    @Schema(description = "Identificador del pago al que se aplica el abono", example = "1")
    private Integer idPago;

    @NotNull(message = "El monto del abono es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto del abono debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2, message = "El monto debe tener maximo 8 digitos enteros y 2 decimales")
    @Schema(description = "Monto del abono", example = "500.00")
    private BigDecimal montoAbono;

    /*@NotNull(message = "La fecha del abono es obligatoria")
    @PastOrPresent(message = "La fecha del abono no puede ser futura")
    @Schema(description = "Momento en que se realizo el abono", example = "2026-05-12T14:30:00")*/
    private LocalDateTime fechaAbono;

    @NotNull(message = "El identificador del metodo de pago es obligatorio")
    @Schema(description = "Identificador del metodo de pago utilizado", example = "1")
    private Byte idMetodoPago;
}
