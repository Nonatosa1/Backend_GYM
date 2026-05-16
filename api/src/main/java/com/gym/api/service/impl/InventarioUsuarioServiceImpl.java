package com.gym.api.service.impl;

import com.gym.api.dto.request.InventarioUsuarioRequestDTO;
import com.gym.api.dto.response.InventarioUsuarioResponseDTO;
import com.gym.api.entity.Inventario;
import com.gym.api.entity.InventarioUsuario;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.InventarioUsuarioMapper;
import com.gym.api.repository.InventarioRepository;
import com.gym.api.repository.InventarioUsuarioRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.InventarioUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato InventarioUsuarioService.
 *
 * Aplica el mismo patron que el servicio de PermisoAcceso, con la diferencia
 * de que aqui las relaciones son hacia Usuario e Inventario (en lugar de
 * Usuario y Area). La logica de validacion de fechas y de resolucion de
 * relaciones es identica, lo cual demuestra como las decisiones de diseño
 * tomadas anteriormente nos permiten resolver problemas similares de
 * manera consistente sin reinventar la rueda.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InventarioUsuarioServiceImpl implements InventarioUsuarioService {

    private final InventarioUsuarioRepository inventarioUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioRepository inventarioRepository;
    private final InventarioUsuarioMapper inventarioUsuarioMapper;

    @Override
    public InventarioUsuarioResponseDTO crear(InventarioUsuarioRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", request.getIdInventario()));

        InventarioUsuario entidad = inventarioUsuarioMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setInventario(inventario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        InventarioUsuario guardado = inventarioUsuarioRepository.save(entidad);
        return inventarioUsuarioMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioUsuarioResponseDTO consultarPorId(Integer id) {
        InventarioUsuario entidad = inventarioUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventarioUsuario", id));
        return inventarioUsuarioMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioUsuarioResponseDTO> listarTodos() {
        return inventarioUsuarioRepository.findAll()
                .stream()
                .map(inventarioUsuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioUsuarioResponseDTO> listarHabilitados() {
        return inventarioUsuarioRepository.findAll()
                .stream()
                .filter(InventarioUsuario::getHabilitado)
                .map(inventarioUsuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public InventarioUsuarioResponseDTO actualizar(Integer id, InventarioUsuarioRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        InventarioUsuario entidad = inventarioUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventarioUsuario", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", request.getIdInventario()));

        inventarioUsuarioMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);
        entidad.setInventario(inventario);

        InventarioUsuario actualizado = inventarioUsuarioRepository.save(entidad);
        return inventarioUsuarioMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        InventarioUsuario entidad = inventarioUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventarioUsuario", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        inventarioUsuarioRepository.save(entidad);
    }

    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new BusinessException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }
}
