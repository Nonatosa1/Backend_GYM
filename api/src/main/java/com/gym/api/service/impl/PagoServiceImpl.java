package com.gym.api.service.impl;

import com.gym.api.dto.DashboardPagosDTO;
import com.gym.api.dto.HistorialPagoDTO;
import com.gym.api.dto.ProximoCargoDTO;
import com.gym.api.dto.request.PagoRequestDTO;
import com.gym.api.dto.response.PagoResponseDTO;
import com.gym.api.entity.Inscripcion;
import com.gym.api.entity.Pago;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.repository.PagoServiciosRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.util.PagoMapper;
import com.gym.api.repository.InscripcionRepository;
import com.gym.api.repository.PagoRepository;
import com.gym.api.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Implementacion del contrato PagoService.
 *
 * La logica es directa porque las validaciones de monto ya estan
 * declaradas en el DTO mediante anotaciones. Solo necesitamos resolver
 * la relacion con Inscripcion, construir la entidad, aplicar la
 * auditoria, y persistir.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagoMapper pagoMapper;
    private final PagoServiciosRepository pagoServiciosRepository;

    @Override
    public PagoResponseDTO crear(PagoRequestDTO request) {
        Inscripcion inscripcion = inscripcionRepository.findById(request.getIdInscripcion())
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", request.getIdInscripcion()));

        Pago entidad = pagoMapper.toEntity(request);
        entidad.setInscripcion(inscripcion);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Pago guardado = pagoRepository.save(entidad);
        return pagoMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDTO consultarPorId(Integer id) {
        Pago entidad = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", id));
        return pagoMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarHabilitados() {
        return pagoRepository.findAll()
                .stream()
                .filter(Pago::getHabilitado)
                .map(pagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PagoResponseDTO actualizar(Integer id, PagoRequestDTO request) {
        Pago entidad = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", id));

        Inscripcion inscripcion = inscripcionRepository.findById(request.getIdInscripcion())
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", request.getIdInscripcion()));

        pagoMapper.updateEntity(entidad, request);
        entidad.setInscripcion(inscripcion);

        Pago actualizado = pagoRepository.save(entidad);
        return pagoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        Pago entidad = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        pagoRepository.save(entidad);
    }

    public DashboardPagosDTO obtenerDashboardPagos(Integer idPersona) {
        Usuario usuario = usuarioRepository.findByPersonaIdPersona(idPersona);
        Integer idUsuario = usuario.getIdUsuario();

        LocalDateTime hace12Meses = LocalDateTime.now().minusMonths(12);
        LocalDate hoy = LocalDate.now();

        // Total pagado últimos 12 meses (suma de ambos flujos)
        BigDecimal totalInscripciones = pagoRepository
                .totalPagadoInscripcionesUltimos12Meses(idUsuario, hace12Meses);
        BigDecimal totalServicios = pagoServiciosRepository
                .totalPagadoServiciosUltimos12Meses(idUsuario, hace12Meses);
        BigDecimal totalPagado = totalInscripciones.add(totalServicios);

        Long ordenesPendientes = pagoRepository.contarOrdenesPendientes(idUsuario);

        // Total movimientos
        Long movimientosInsc = pagoRepository.totalMovimientosInscripciones(idUsuario);
        Long movimientosServ = pagoServiciosRepository.totalMovimientosServicios(idUsuario);
        Long totalMovimientos = movimientosInsc + movimientosServ;

        //Monto total a aportar
        BigDecimal montoPorAportar = pagoRepository.montoTotalPorAportar(idUsuario);

        // Próximo cargo pendiente
        ProximoCargoDTO proximoCargo = obtenerProximoCargo(idUsuario, hoy);

        // Historial unificado: abonos realizados + órdenes pendientes + servicios
        List<HistorialPagoDTO> historial = new ArrayList<>();
        historial.addAll(pagoRepository.historialInscripciones(idUsuario));
        historial.addAll(pagoRepository.historialOrdenesPendientes(idUsuario));
        historial.addAll(pagoServiciosRepository.historialServicios(idUsuario));
        historial.sort(Comparator.comparing(HistorialPagoDTO::getFecha).reversed());

        return DashboardPagosDTO.builder()
                .totalPagadoUltimos12Meses(totalPagado)
                .pagosPendientes(ordenesPendientes)
                .totalMovimientos(totalMovimientos)
                .montoTotalPorAportar(montoPorAportar)
                .proximoCargoPendiente(proximoCargo)
                .historialPagos(historial)
                .build();
    }

    private ProximoCargoDTO obtenerProximoCargo(Integer idUsuario, LocalDate hoy) {
        List<Inscripcion> proximas = inscripcionRepository
                .proximaInscripcionPorVencer(idUsuario, hoy);

        if (proximas.isEmpty()) {
            return null;
        }

        Inscripcion proxima = proximas.get(0);
        long dias = ChronoUnit.DAYS.between(hoy, proxima.getFechaVencimiento());

        return ProximoCargoDTO.builder()
                .fechaVencimiento(proxima.getFechaVencimiento())
                .concepto("Renovación: " + proxima.getMembresia().getMembresia())
                .montoEstimado(proxima.getMembresia().getPrecio())
                .diasRestantes(dias)
                .build();
    }

}
