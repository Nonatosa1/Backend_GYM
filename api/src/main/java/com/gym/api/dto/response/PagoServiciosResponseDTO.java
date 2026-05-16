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
 * DTO que representa los datos de un pago de servicios devueltos por la API.
 *
 * Incluye informacion anidada tanto de la renta que se esta pagando como
 * del metodo de pago utilizado. Esto permite que el cliente vea en una
 * sola peticion el contexto completo del pago, incluyendo a quien
 * corresponde y como se efectuo.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un pago de servicios devueltos por la API")
public class PagoServiciosResponseDTO {

    @Schema(description = "Identificador unico del pago", example = "1")
    private Integer idPagoServicio;

    @Schema(description = "Renta de servicios que se esta pagando")
    private RentaServiciosResponseDTO rentaServicios;

    @Schema(description = "Monto total del pago", example = "150.00")
    private BigDecimal total;

    @Schema(description = "Metodo de pago utilizado")
    private MetodoPagoResponseDTO metodoPago;

    @Schema(description = "Indica si el pago esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
