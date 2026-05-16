package com.gym.api.service.impl;

import com.gym.api.dto.request.DetallePagoRequestDTO;
import com.gym.api.dto.response.DetallePagoResponseDTO;
import com.gym.api.entity.DetallePago;
import com.gym.api.entity.MetodoPago;
import com.gym.api.entity.Pago;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.DetallePagoMapper;
import com.gym.api.repository.DetallePagoRepository;
import com.gym.api.repository.MetodoPagoRepository;
import com.gym.api.repository.PagoRepository;
import com.gym.api.service.DetallePagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato DetallePagoService.
 *
 * Coordina la resolucion de dos relaciones (el Pago al que se aplica el
 * abono y el MetodoPago utilizado), aplica las validaciones declaradas
 * en el DTO mediante anotaciones, y gestiona la auditoria automatica.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DetallePagoServiceImpl implements DetallePagoService {

    private final DetallePagoRepository detallePagoRepository;
    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final DetallePagoMapper detallePagoMapper;

    @Override
    public DetallePagoResponseDTO crear(DetallePagoRequestDTO request) {
        Pago pago = pagoRepository.findById(request.getIdPago())
                .orElseThrow(() -> new ResourceNotFoundException("Pago", request.getIdPago()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", request.getIdMetodoPago()));

        DetallePago entidad = detallePagoMapper.toEntity(request);
        entidad.setPago(pago);
        entidad.setMetodoPago(metodoPago);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        DetallePago guardado = detallePagoRepository.save(entidad);
        return detallePagoMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePagoResponseDTO consultarPorId(Integer id) {
        DetallePago entidad = detallePagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePago", id));
        return detallePagoMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePagoResponseDTO> listarTodos() {
        return detallePagoRepository.findAll()
                .stream()
                .map(detallePagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePagoResponseDTO> listarHabilitados() {
        return detallePagoRepository.findAll()
                .stream()
                .filter(DetallePago::getHabilitado)
                .map(detallePagoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public DetallePagoResponseDTO actualizar(Integer id, DetallePagoRequestDTO request) {
        DetallePago entidad = detallePagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePago", id));

        Pago pago = pagoRepository.findById(request.getIdPago())
                .orElseThrow(() -> new ResourceNotFoundException("Pago", request.getIdPago()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", request.getIdMetodoPago()));

        detallePagoMapper.updateEntity(entidad, request);
        entidad.setPago(pago);
        entidad.setMetodoPago(metodoPago);

        DetallePago actualizado = detallePagoRepository.save(entidad);
        return detallePagoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        DetallePago entidad = detallePagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePago", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        detallePagoRepository.save(entidad);
    }
}
