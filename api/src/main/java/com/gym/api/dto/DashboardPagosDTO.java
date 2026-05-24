package com.gym.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPagosDTO {
    private BigDecimal totalPagadoUltimos12Meses;
    private BigDecimal pagosPendientes;
    private Long totalMovimientos;
    private ProximoCargoDTO proximoCargoPendiente;
    private List<HistorialPagoDTO> historialPagos;
}
