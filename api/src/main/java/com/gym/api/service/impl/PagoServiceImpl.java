package com.gym.api.service.impl;

import com.gym.api.dto.request.PagoRequestDTO;
import com.gym.api.dto.response.PagoResponseDTO;
import com.gym.api.entity.Inscripcion;
import com.gym.api.entity.Pago;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.PagoMapper;
import com.gym.api.repository.InscripcionRepository;
import com.gym.api.repository.PagoRepository;
import com.gym.api.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private final PagoMapper pagoMapper;

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
}
