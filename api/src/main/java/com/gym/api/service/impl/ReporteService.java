package com.gym.api.service.impl;
import com.gym.api.dto.*;
import com.gym.api.repository.InscripcionRepository;
import com.gym.api.repository.PagoRepository;
import com.gym.api.repository.PagoServiciosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private static final int DIAS_VENTANA = 30;
    private static final int DIAS_URGENTE = 3;
    private static final int DIAS_ESTA_SEMANA = 7;
    private static final String ESTADO_URGENTE = "URGENTE";
    private static final String ESTADO_ESTA_SEMANA = "ESTA_SEMANA";
    private static final String ESTADO_EN_PLAZO = "EN_PLAZO";

    private final InscripcionRepository inscripcionRepository;
    private final PagoRepository pagoRepository;
    private final PagoServiciosRepository pagoServiciosRepository;

    public ReporteMembresiasDTO generarReporte() {
        LocalDate hoy = LocalDate.now();
        LocalDate hace30Dias = hoy.minusDays(30);

        Long activos = inscripcionRepository.contarMiembrosActivos(hoy);
        Long nuevasAltas = inscripcionRepository.contarNuevasAltas(hace30Dias, hoy);
        Long vencidas = inscripcionRepository.contarMembresiasVencidas(hace30Dias, hoy);

        // Traemos la lista y completamos diasRestantes
        List<MiembroActivoDTO> vigentes = inscripcionRepository.listarMiembrosVigentes(hoy);
        vigentes.forEach(m -> m.setDiasRestantes(
                ChronoUnit.DAYS.between(hoy, m.getFechaVencimiento())
        ));

        return ReporteMembresiasDTO.builder()
                .totalMiembrosActivos(activos)
                .nuevasAltasUltimos30Dias(nuevasAltas)
                .membresiasVencidasUltimos30Dias(vencidas)
                .miembrosVigentes(vigentes)
                .build();
    }

    public ReporteIngresosDTO generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        // Convertimos las fechas a rango completo del día
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        // Unimos ambos tipos de transacciones
        List<TransaccionDTO> transacciones = new ArrayList<>();
        transacciones.addAll(pagoRepository.transaccionesInscripcionesEnRango(inicio, fin));
        transacciones.addAll(pagoServiciosRepository.transaccionesServiciosEnRango(inicio, fin));

        // Ordenamos por fecha descendente
        transacciones.sort(Comparator.comparing(TransaccionDTO::getFecha).reversed());

        // Calculamos totales en memoria sobre el listado ya unificado
        BigDecimal totalIngresado = transacciones.stream()
                .map(TransaccionDTO::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long totalTransacciones = (long) transacciones.size();

        BigDecimal promedio = totalTransacciones > 0
                ? totalIngresado.divide(
                BigDecimal.valueOf(totalTransacciones),
                2,
                RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return ReporteIngresosDTO.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .totalIngresado(totalIngresado)
                .totalTransacciones(totalTransacciones)
                .promedioPago(promedio)
                .transacciones(transacciones)
                .build();
    }

    public ReporteVencimientosDTO reporteVencimiento() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(DIAS_VENTANA);

        List<MiembroPorVencerDTO> miembros = pagoRepository
                .listarProximosVencer(hoy, fechaLimite);

        long urgentes = 0;
        long estaSemana = 0;

        for (MiembroPorVencerDTO m : miembros) {
            long dias = ChronoUnit.DAYS.between(hoy, m.getFechaVencimiento());
            m.setDiasParaVencer(dias);
            m.setEstado(calcularEstado(dias));

            if (dias <= DIAS_URGENTE) {
                urgentes++;
                estaSemana++;  // urgente también cae en "esta semana"
            } else if (dias <= DIAS_ESTA_SEMANA) {
                estaSemana++;
            }
        }

        return ReporteVencimientosDTO.builder()
                .totalProximosVencer((long) miembros.size())
                .totalUrgentes(urgentes)
                .totalEstaSemana(estaSemana)
                .miembros(miembros)
                .build();
    }

    private String calcularEstado(long diasParaVencer) {
        if (diasParaVencer <= DIAS_URGENTE) {
            return ESTADO_URGENTE;
        } else if (diasParaVencer <= DIAS_ESTA_SEMANA) {
            return ESTADO_ESTA_SEMANA;
        } else {
            return ESTADO_EN_PLAZO;
        }
    }
}
