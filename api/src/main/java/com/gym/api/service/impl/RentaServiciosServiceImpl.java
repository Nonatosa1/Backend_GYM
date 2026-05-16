package com.gym.api.service.impl;

import com.gym.api.dto.request.RentaServiciosRequestDTO;
import com.gym.api.dto.response.RentaServiciosResponseDTO;
import com.gym.api.entity.Inventario;
import com.gym.api.entity.RentaServicios;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.RentaServiciosMapper;
import com.gym.api.repository.InventarioRepository;
import com.gym.api.repository.RentaServiciosRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.RentaServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato RentaServiciosService.
 *
 * Aplica los patrones que ya conoces: resolucion de relaciones por ID,
 * validacion semantica de fechas (vencimiento posterior al inicio), y
 * auditoria automatica. La unica particularidad respecto a otros
 * servicios similares es la nomenclatura "fechaVence" en lugar de
 * "fechaFin", que refleja la semantica del dominio de rentas.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RentaServiciosServiceImpl implements RentaServiciosService {

    private final RentaServiciosRepository rentaServiciosRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioRepository inventarioRepository;
    private final RentaServiciosMapper rentaServiciosMapper;

    @Override
    public RentaServiciosResponseDTO crear(RentaServiciosRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaVence());

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", request.getIdInventario()));

        RentaServicios entidad = rentaServiciosMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setInventario(inventario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        RentaServicios guardada = rentaServiciosRepository.save(entidad);
        return rentaServiciosMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public RentaServiciosResponseDTO consultarPorId(Integer id) {
        RentaServicios entidad = rentaServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentaServicios", id));
        return rentaServiciosMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentaServiciosResponseDTO> listarTodos() {
        return rentaServiciosRepository.findAll()
                .stream()
                .map(rentaServiciosMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentaServiciosResponseDTO> listarHabilitados() {
        return rentaServiciosRepository.findAll()
                .stream()
                .filter(RentaServicios::getHabilitado)
                .map(rentaServiciosMapper::toResponseDTO)
                .toList();
    }

    @Override
    public RentaServiciosResponseDTO actualizar(Integer id, RentaServiciosRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaVence());

        RentaServicios entidad = rentaServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentaServicios", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", request.getIdInventario()));

        rentaServiciosMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);
        entidad.setInventario(inventario);

        RentaServicios actualizada = rentaServiciosRepository.save(entidad);
        return rentaServiciosMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        RentaServicios entidad = rentaServiciosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RentaServicios", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        rentaServiciosRepository.save(entidad);
    }

    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaVence) {
        if (!fechaVence.isAfter(fechaInicio)) {
            throw new BusinessException("La fecha de vencimiento debe ser posterior a la fecha de inicio");
        }
    }
}
