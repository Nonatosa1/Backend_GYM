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

/**
 * DTO que representa los datos de un pago devueltos por la API.
 *
 * Incluye informacion anidada de la inscripcion asociada, lo cual a su
 * vez incluye la membresia adquirida y el usuario inscrito. Esto permite
 * al cliente ver en una sola peticion el contexto completo del pago,
 * desde el monto comprometido hasta los detalles del cliente.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un pago devueltos por la API")
public class PagoResponseDTO {

    @Schema(description = "Identificador unico del pago", example = "1")
    private Integer idPago;

    @Schema(description = "Inscripcion asociada al pago")
    private InscripcionResponseDTO inscripcion;

    @Schema(description = "Monto total comprometido", example = "899.99")
    private BigDecimal montoTotal;

    @Schema(description = "Indica si el pago esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
