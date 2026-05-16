package com.gym.api.service.impl;

import com.gym.api.dto.request.MetodoPagoRequestDTO;
import com.gym.api.dto.response.MetodoPagoResponseDTO;
import com.gym.api.entity.MetodoPago;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.MetodoPagoMapper;
import com.gym.api.repository.MetodoPagoRepository;
import com.gym.api.service.MetodoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MetodoPagoServiceImpl implements MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;
    private final MetodoPagoMapper metodoPagoMapper;

    @Override
    public MetodoPagoResponseDTO crear(MetodoPagoRequestDTO request) {
        MetodoPago entidad = metodoPagoMapper.toEntity(request);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        MetodoPago guardado = metodoPagoRepository.save(entidad);
        return metodoPagoMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public MetodoPagoResponseDTO consultarPorId(Byte id) {
        MetodoPago entidad = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", id));
        return metodoPagoMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetodoPagoResponseDTO> listarTodos() {
        return metodoPagoRepository.findAll()
                .stream()
                .map(metodoPagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetodoPagoResponseDTO> listarHabilitados() {
        return metodoPagoRepository.findAll()
                .stream()
                .filter(MetodoPago::getHabilitado)
                .map(metodoPagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public MetodoPagoResponseDTO actualizar(Byte id, MetodoPagoRequestDTO request) {
        MetodoPago entidad = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", id));

        metodoPagoMapper.updateEntity(entidad, request);

        MetodoPago actualizado = metodoPagoRepository.save(entidad);
        return metodoPagoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Byte id) {
        MetodoPago entidad = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        metodoPagoRepository.save(entidad);
    }
}
