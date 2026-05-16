package com.gym.api.service.impl;

import com.gym.api.dto.request.TipoInventarioRequestDTO;
import com.gym.api.dto.response.TipoInventarioResponseDTO;
import com.gym.api.entity.TipoInventario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.TipoInventarioMapper;
import com.gym.api.repository.TipoInventarioRepository;
import com.gym.api.service.TipoInventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TipoInventarioServiceImpl implements TipoInventarioService {

    private final TipoInventarioRepository tipoInventarioRepository;
    private final TipoInventarioMapper tipoInventarioMapper;

    @Override
    public TipoInventarioResponseDTO crear(TipoInventarioRequestDTO request) {
        TipoInventario entidad = tipoInventarioMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        TipoInventario guardado = tipoInventarioRepository.save(entidad);
        return tipoInventarioMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoInventarioResponseDTO consultarPorId(Byte id) {
        TipoInventario entidad = tipoInventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoInventario", id));
        return tipoInventarioMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoInventarioResponseDTO> listarTodos() {
        return tipoInventarioRepository.findAll()
                .stream()
                .map(tipoInventarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoInventarioResponseDTO> listarHabilitados() {
        return tipoInventarioRepository.findAll()
                .stream()
                .filter(TipoInventario::getHabilitado)
                .map(tipoInventarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TipoInventarioResponseDTO actualizar(Byte id, TipoInventarioRequestDTO request) {
        TipoInventario entidad = tipoInventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoInventario", id));

        tipoInventarioMapper.updateEntity(entidad, request);

        TipoInventario actualizado = tipoInventarioRepository.save(entidad);
        return tipoInventarioMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Byte id) {
        TipoInventario entidad = tipoInventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoInventario", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        tipoInventarioRepository.save(entidad);
    }
}
