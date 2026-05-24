package com.gym.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Reporte de membresías próximas a vencer en los siguientes 30 días")
public class ReporteVencimientosDTO {

    @Schema(description = "Total de miembros cuya membresía vence en los próximos 30 días", example = "42")
    private Long totalProximosVencer;

    @Schema(description = "Miembros con renovación urgente (3 días o menos para vencer)", example = "5")
    private Long totalUrgentes;

    @Schema(description = "Miembros que vencen esta semana (7 días o menos)", example = "12")
    private Long totalEstaSemana;

    @Schema(description = "Listado detallado de miembros por vencer ordenados por proximidad")
    private List<MiembroPorVencerDTO> miembros;
}