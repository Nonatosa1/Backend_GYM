package com.gym.api.service.impl;

import com.gym.api.dto.request.ClaseDiaRequestDTO;
import com.gym.api.dto.response.ClaseDiaResponseDTO;
import com.gym.api.entity.Clase;
import com.gym.api.entity.ClaseDia;
import com.gym.api.entity.Dia;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.ClaseDiaMapper;
import com.gym.api.repository.ClaseDiaRepository;
import com.gym.api.repository.ClaseRepository;
import com.gym.api.repository.DiaRepository;
import com.gym.api.service.ClaseDiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato ClaseDiaService.
 *
 * Como la entidad es una tabla puente sin atributos propios, la logica
 * del servicio es directa: resolver las dos relaciones (Clase y Dia),
 * crear la entidad, aplicar la auditoria, y persistir. No hay validaciones
 * semanticas adicionales porque la entidad no tiene campos propios que
 * validar.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClaseDiaServiceImpl implements ClaseDiaService {

    private final ClaseDiaRepository claseDiaRepository;
    private final ClaseRepository claseRepository;
    private final DiaRepository diaRepository;
    private final ClaseDiaMapper claseDiaMapper;

    @Override
    public ClaseDiaResponseDTO crear(ClaseDiaRequestDTO request) {
        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Dia dia = diaRepository.findById(request.getIdDia())
                .orElseThrow(() -> new ResourceNotFoundException("Dia", request.getIdDia()));

        ClaseDia entidad = claseDiaMapper.toEntity(request);
        entidad.setClase(clase);
        entidad.setDia(dia);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        ClaseDia guardado = claseDiaRepository.save(entidad);
        return claseDiaMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseDiaResponseDTO consultarPorId(Integer id) {
        ClaseDia entidad = claseDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseDia", id));
        return claseDiaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseDiaResponseDTO> listarTodos() {
        return claseDiaRepository.findAll()
                .stream()
                .map(claseDiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseDiaResponseDTO> listarHabilitados() {
        return claseDiaRepository.findAll()
                .stream()
                .filter(ClaseDia::getHabilitado)
                .map(claseDiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ClaseDiaResponseDTO actualizar(Integer id, ClaseDiaRequestDTO request) {
        ClaseDia entidad = claseDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseDia", id));

        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Dia dia = diaRepository.findById(request.getIdDia())
                .orElseThrow(() -> new ResourceNotFoundException("Dia", request.getIdDia()));

        claseDiaMapper.updateEntity(entidad, request);
        entidad.setClase(clase);
        entidad.setDia(dia);

        ClaseDia actualizado = claseDiaRepository.save(entidad);
        return claseDiaMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        ClaseDia entidad = claseDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClaseDia", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        claseDiaRepository.save(entidad);
    }
}
