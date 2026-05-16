package com.gym.api.service.impl;

import com.gym.api.dto.request.MembresiaRequestDTO;
import com.gym.api.dto.response.MembresiaResponseDTO;
import com.gym.api.entity.Membresia;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.MembresiaMapper;
import com.gym.api.repository.MembresiaRepository;
import com.gym.api.service.MembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MembresiaServiceImpl implements MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final MembresiaMapper membresiaMapper;

    @Override
    public MembresiaResponseDTO crear(MembresiaRequestDTO request) {
        Membresia entidad = membresiaMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Membresia guardada = membresiaRepository.save(entidad);
        return membresiaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public MembresiaResponseDTO consultarPorId(Short id) {
        Membresia entidad = membresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", id));
        return membresiaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembresiaResponseDTO> listarTodos() {
        return membresiaRepository.findAll()
                .stream()
                .map(membresiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembresiaResponseDTO> listarHabilitados() {
        return membresiaRepository.findAll()
                .stream()
                .filter(Membresia::getHabilitado)
                .map(membresiaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public MembresiaResponseDTO actualizar(Short id, MembresiaRequestDTO request) {
        Membresia entidad = membresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", id));

        membresiaMapper.updateEntity(entidad, request);

        Membresia actualizada = membresiaRepository.save(entidad);
        return membresiaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Short id) {
        Membresia entidad = membresiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        membresiaRepository.save(entidad);
    }
}
