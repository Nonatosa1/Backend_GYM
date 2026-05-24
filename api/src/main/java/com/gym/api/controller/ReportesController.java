package com.gym.api.controller;

import com.gym.api.dto.ReporteIngresosDTO;
import com.gym.api.dto.ReporteMembresiasDTO;
import com.gym.api.dto.ReporteVencimientosDTO;
import com.gym.api.service.impl.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {
    private final ReporteService reporteService;

    @Operation(
            summary = "Reporte general de membresías",
            description = """
                    Genera un reporte con los siguientes indicadores:
                    - Total de miembros activos al día de hoy.
                    - Nuevas altas registradas en los últimos 30 días.
                    - Membresías que se vencieron en los últimos 30 días.
                    - Listado detallado de todos los miembros con membresía vigente.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte generado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteMembresiasDTO.class))
            ),
            @ApiResponse(responseCode = "500", description = "Error interno al generar el reporte")
    })
    @GetMapping("/membresias")
    public ResponseEntity<ReporteMembresiasDTO> getReporteMembresias() {
        return ResponseEntity.ok(reporteService.generarReporte());
    }

    @Operation(
            summary = "Reporte de ingresos por rango de fechas",
            description = """
                    Genera un reporte financiero del periodo indicado que incluye:
                    - Total monetario ingresado.
                    - Cantidad de transacciones realizadas.
                    - Promedio de pago por transacción.
                    - Listado detallado de todas las transacciones (membresías y rentas de equipo)
                      con id, fecha, miembro, concepto y monto.
                    
                    El rango de fechas es inclusivo en ambos extremos: incluye todas las
                    transacciones desde las 00:00:00 de la fecha de inicio hasta las 23:59:59
                    de la fecha fin.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte generado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteIngresosDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parámetros de fecha inválidos o faltantes"),
            @ApiResponse(responseCode = "500", description = "Error interno al generar el reporte")
    })
    @GetMapping("/ingresos")
    public ResponseEntity<ReporteIngresosDTO> getReporteIngresos(
            @Parameter(
                    description = "Fecha de inicio del periodo (formato YYYY-MM-DD)",
                    example = "2026-01-01",
                    required = true
            )
            @RequestParam("fechaInicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,

            @Parameter(
                    description = "Fecha fin del periodo (formato YYYY-MM-DD)",
                    example = "2026-05-21",
                    required = true
            )
            @RequestParam("fechaFin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        return ResponseEntity.ok(reporteService.generarReporte(fechaInicio, fechaFin));
    }

    @Operation(
            summary = "Reporte de membresías próximas a vencer",
            description = """
                Genera un reporte con los miembros cuya membresía vence en los próximos
                30 días, clasificados por urgencia de renovación:
                
                **Indicadores:**
                - Total de miembros con vencimiento en los próximos 30 días.
                - Total urgentes: vencen en 3 días o menos.
                - Total de esta semana: vencen en 7 días o menos (incluye urgentes).
                
                **Listado:** cada registro incluye nombre, correo, tipo de membresía,
                fecha de vencimiento, días restantes y un estado calculado:
                - `URGENTE`: 3 días o menos.
                - `ESTA_SEMANA`: entre 4 y 7 días.
                - `EN_PLAZO`: entre 8 y 30 días.
                
                Los resultados vienen ordenados por proximidad de vencimiento (los más
                urgentes primero).
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reporte generado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteVencimientosDTO.class))
            ),
            @ApiResponse(responseCode = "500", description = "Error interno al generar el reporte")
    })
    @GetMapping("/vencimientos")
    public ResponseEntity<ReporteVencimientosDTO> getReporteVencimientos() {
        return ResponseEntity.ok(reporteService.reporteVencimiento());
    }
}
