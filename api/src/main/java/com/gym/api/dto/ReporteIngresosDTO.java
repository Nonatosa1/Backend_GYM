package com.gym.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Reporte de ingresos generados dentro de un rango de fechas")
public class ReporteIngresosDTO {

    @Schema(description = "Fecha de inicio del periodo consultado", example = "2026-01-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha fin del periodo consultado", example = "2026-05-21")
    private LocalDate fechaFin;

    @Schema(description = "Suma total del dinero ingresado en el periodo", example = "45230.50")
    private BigDecimal totalIngresado;

    @Schema(description = "Cantidad de transacciones registradas en el periodo", example = "127")
    private Long totalTransacciones;

    @Schema(description = "Monto promedio por transacción", example = "356.15")
    private BigDecimal promedioPago;

    @Schema(description = "Listado detallado de todas las transacciones del periodo")
    private List<TransaccionDTO> transacciones;
}
