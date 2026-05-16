package com.gym.api.service.impl;

import com.gym.api.dto.request.DiaRequestDTO;
import com.gym.api.dto.response.DiaResponseDTO;
import com.gym.api.entity.Dia;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.DiaMapper;
import com.gym.api.repository.DiaRepository;
import com.gym.api.service.DiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaServiceImpl implements DiaService {

    private final DiaRepository diaRepository;
    private final DiaMapper diaMapper;

    @Override
    public DiaResponseDTO crear(DiaRequestDTO request) {
        Dia entidad = diaMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Dia guardado = diaRepository.save(entidad);
        return diaMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public DiaResponseDTO consultarPorId(Byte id) {
        Dia entidad = diaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dia", id));
        return diaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaResponseDTO> listarTodos() {
        return diaRepository.findAll()
                .stream()
                .map(diaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiaResponseDTO> listarHabilitados() {
        return diaRepository.findAll()
                .stream()
                .filter(Dia::getHabilitado)
                .map(diaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public DiaResponseDTO actualizar(Byte id, DiaRequestDTO request) {
        Dia entidad = diaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dia", id));

        diaMapper.updateEntity(entidad, request);

        Dia actualizado = diaRepository.save(entidad);
        return diaMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Byte id) {
        Dia entidad = diaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dia", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        diaRepository.save(entidad);
    }
}
