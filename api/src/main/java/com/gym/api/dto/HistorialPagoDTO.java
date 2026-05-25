package com.gym.api.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialPagoDTO {
    private LocalDateTime fecha;
    private String concepto;
    private String metodo;
    private BigDecimal monto;
    private String estadoPago;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idPago;
}
