package com.gym.api.service.impl;

import com.gym.api.dto.request.ActividadRequestDTO;
import com.gym.api.dto.response.ActividadResponseDTO;
import com.gym.api.entity.Actividad;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.ActividadMapper;
import com.gym.api.repository.ActividadRepository;
import com.gym.api.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;
    private final ActividadMapper actividadMapper;

    @Override
    public ActividadResponseDTO crear(ActividadRequestDTO request) {
        Actividad entidad = actividadMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Actividad guardado = actividadRepository.save(entidad);
        return actividadMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ActividadResponseDTO consultarPorId(Byte id) {
        Actividad entidad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", id));
        return actividadMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadResponseDTO> listarTodos() {
        return actividadRepository.findAll()
                .stream()
                .map(actividadMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadResponseDTO> listarHabilitados() {
        return actividadRepository.findAll()
                .stream()
                .filter(Actividad::getHabilitado)
                .map(actividadMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ActividadResponseDTO actualizar(Byte id, ActividadRequestDTO request) {
        Actividad entidad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", id));

        actividadMapper.updateEntity(entidad, request);

        Actividad actualizado = actividadRepository.save(entidad);
        return actividadMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Byte id) {
        Actividad entidad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        actividadRepository.save(entidad);
    }
}
