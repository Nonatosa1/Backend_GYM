package com.gym.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMembresiasDTO {
    private Long totalMiembrosActivos;
    private Long nuevasAltasUltimos30Dias;
    private Long membresiasVencidasUltimos30Dias;
    private List<MiembroActivoDTO> miembrosVigentes;
}
