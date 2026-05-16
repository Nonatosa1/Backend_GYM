package com.gym.api.service.impl;

import com.gym.api.dto.request.PagoServiciosRequestDTO;
import com.gym.api.dto.response.PagoServiciosResponseDTO;
import com.gym.api.entity.MetodoPago;
import com.gym.api.entity.PagoServicios;
import com.gym.api.entity.RentaServicios;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.PagoServiciosMapper;
import com.gym.api.repository.MetodoPagoRepository;
import com.gym.api.repository.PagoServiciosRepository;
import com.gym.api.repository.RentaServiciosRepository;
import com.gym.api.service.PagoServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato PagoServiciosService.
 *
 * Este servicio cierra el ciclo financiero del bloque de inventario.
 * Cada pago referencia a una renta de servicios especifica (la
 * transaccion que se esta cobrando) y al metodo de pago utilizado para
 * efectuar el cobro. La logica es relativamente directa: resolver ambas
 * relaciones, validar que las entidades existan, y persistir el registro.
 *
 * Las validaciones de monto (positividad, limites de precision decimal)
 * estan declaradas en el DTO mediante anotaciones, asi que cuando el
 * servicio recibe la peticion sabe que el monto ya cumple esos
 * requisitos. Esto es un buen ejemplo de como las validaciones declarativas
 * del DTO complementan las validaciones de negocio del servicio: cada una
 * cubre lo que es apropiado para su nivel.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiciosServiceImpl implements PagoServiciosService {

    private final PagoServiciosRepository pagoServiciosRepository;
    private final RentaServiciosRepository rentaServiciosRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final PagoServiciosMapper pagoServiciosMapper;

    @Override
    public PagoServiciosResponseDTO crear(PagoServiciosRequestDTO request) {
        RentaServicios rentaServicios = rentaServiciosRepository.findById(request.getIdRentaServicio())
                .orElseThrow(() -> new ResourceNotFoundException("RentaServicios", request.getIdRentaServicio()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", request.getIdMetodoPago()));

        PagoServicios entidad = pagoServiciosMapper.toEntity(request);
        entidad.setRentaServicios(rentaServicios);
        entidad.setMetodoPago(metodoPago);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        PagoServicios guardado = pagoServiciosRepository.save(entidad);
        return pagoServiciosMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoServiciosResponseDTO consultarPorId(Integer id) {
        PagoServicios entidad = pagoServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PagoServicios", id));
        return pagoServiciosMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoServiciosResponseDTO> listarTodos() {
        return pagoServiciosRepository.findAll()
                .stream()
                .map(pagoServiciosMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoServiciosResponseDTO> listarHabilitados() {
        return pagoServiciosRepository.findAll()
                .stream()
                .filter(PagoServicios::getHabilitado)
                .map(pagoServiciosMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PagoServiciosResponseDTO actualizar(Integer id, PagoServiciosRequestDTO request) {
        PagoServicios entidad = pagoServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PagoServicios", id));

        RentaServicios rentaServicios = rentaServiciosRepository.findById(request.getIdRentaServicio())
                .orElseThrow(() -> new ResourceNotFoundException("RentaServicios", request.getIdRentaServicio()));

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago", request.getIdMetodoPago()));

        pagoServiciosMapper.updateEntity(entidad, request);
        entidad.setRentaServicios(rentaServicios);
        entidad.setMetodoPago(metodoPago);

        PagoServicios actualizado = pagoServiciosRepository.save(entidad);
        return pagoServiciosMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        PagoServicios entidad = pagoServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PagoServicios", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        pagoServiciosRepository.save(entidad);
    }
}
