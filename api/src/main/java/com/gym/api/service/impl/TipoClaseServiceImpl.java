package com.gym.api.service.impl;

import com.gym.api.dto.request.TipoClaseRequestDTO;
import com.gym.api.dto.response.TipoClaseResponseDTO;
import com.gym.api.entity.TipoClase;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.TipoClaseMapper;
import com.gym.api.repository.TipoClaseRepository;
import com.gym.api.service.TipoClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TipoClaseServiceImpl implements TipoClaseService {

    private final TipoClaseRepository tipoClaseRepository;
    private final TipoClaseMapper tipoClaseMapper;

    @Override
    public TipoClaseResponseDTO crear(TipoClaseRequestDTO request) {
        TipoClase entidad = tipoClaseMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        TipoClase guardado = tipoClaseRepository.save(entidad);
        return tipoClaseMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoClaseResponseDTO consultarPorId(Byte id) {
        TipoClase entidad = tipoClaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoClase", id));
        return tipoClaseMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoClaseResponseDTO> listarTodos() {
        return tipoClaseRepository.findAll()
                .stream()
                .map(tipoClaseMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoClaseResponseDTO> listarHabilitados() {
        return tipoClaseRepository.findAll()
                .stream()
                .filter(TipoClase::getHabilitado)
                .map(tipoClaseMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TipoClaseResponseDTO actualizar(Byte id, TipoClaseRequestDTO request) {
        TipoClase entidad = tipoClaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoClase", id));

        tipoClaseMapper.updateEntity(entidad, request);

        TipoClase actualizado = tipoClaseRepository.save(entidad);
        return tipoClaseMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Byte id) {
        TipoClase entidad = tipoClaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoClase", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        tipoClaseRepository.save(entidad);
    }
}
