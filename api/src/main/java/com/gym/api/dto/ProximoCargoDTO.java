package com.gym.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProximoCargoDTO {
    private LocalDate fechaVencimiento;
    private String concepto;
    private BigDecimal montoEstimado;
    private Long diasRestantes;
}
