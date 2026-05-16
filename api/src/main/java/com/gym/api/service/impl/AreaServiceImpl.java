package com.gym.api.service.impl;

import com.gym.api.dto.request.AreaRequestDTO;
import com.gym.api.dto.response.AreaResponseDTO;
import com.gym.api.entity.Area;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.AreaMapper;
import com.gym.api.repository.AreaRepository;
import com.gym.api.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    @Override
    public AreaResponseDTO crear(AreaRequestDTO request) {
        Area entidad = areaMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Area guardada = areaRepository.save(entidad);
        return areaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AreaResponseDTO consultarPorId(Integer id) {
        Area entidad = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area", id));
        return areaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaResponseDTO> listarTodos() {
        return areaRepository.findAll()
                .stream()
                .map(areaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaResponseDTO> listarHabilitados() {
        return areaRepository.findAll()
                .stream()
                .filter(Area::getHabilitado)
                .map(areaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AreaResponseDTO actualizar(Integer id, AreaRequestDTO request) {
        Area entidad = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area", id));

        areaMapper.updateEntity(entidad, request);

        Area actualizada = areaRepository.save(entidad);
        return areaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Area entidad = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        areaRepository.save(entidad);
    }
}
