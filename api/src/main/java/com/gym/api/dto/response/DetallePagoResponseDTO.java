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
@Schema(description = "Datos de un detalle de pago devueltos por la API")
public class DetallePagoResponseDTO {

    @Schema(description = "Identificador unico del detalle de pago", example = "1")
    private Integer idDetallePago;

    @Schema(description = "Pago al que se aplica este abono")
    private PagoResponseDTO pago;

    @Schema(description = "Monto del abono", example = "500.00")
    private BigDecimal montoAbono;

    @Schema(description = "Momento en que se realizo el abono", example = "2026-05-12T14:30:00")
    private LocalDateTime fechaAbono;

    @Schema(description = "Metodo de pago utilizado")
    private MetodoPagoResponseDTO metodoPago;

    @Schema(description = "Indica si el detalle esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
