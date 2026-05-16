package com.gym.api.service.impl;

import com.gym.api.dto.request.InventarioRequestDTO;
import com.gym.api.dto.response.InventarioResponseDTO;
import com.gym.api.entity.Area;
import com.gym.api.entity.Inventario;
import com.gym.api.entity.TipoInventario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.InventarioMapper;
import com.gym.api.repository.AreaRepository;
import com.gym.api.repository.InventarioRepository;
import com.gym.api.repository.TipoInventarioRepository;
import com.gym.api.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato InventarioService.
 *
 * Sigue el patron establecido para servicios con multiples relaciones:
 * resuelve cada referencia por ID consultando el repositorio
 * correspondiente, fallando rapidamente si alguna entidad referenciada
 * no existe. Solo cuando todas las validaciones pasan, procede a
 * construir y persistir la nueva entidad de inventario.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final AreaRepository areaRepository;
    private final TipoInventarioRepository tipoInventarioRepository;
    private final InventarioMapper inventarioMapper;

    @Override
    public InventarioResponseDTO crear(InventarioRequestDTO request) {
        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        TipoInventario tipoInventario = tipoInventarioRepository.findById(request.getIdTipoInventario())
                .orElseThrow(() -> new ResourceNotFoundException("TipoInventario", request.getIdTipoInventario()));

        Inventario entidad = inventarioMapper.toEntity(request);
        entidad.setArea(area);
        entidad.setTipoInventario(tipoInventario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Inventario guardado = inventarioRepository.save(entidad);
        return inventarioMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponseDTO consultarPorId(Integer id) {
        Inventario entidad = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", id));
        return inventarioMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> listarTodos() {
        return inventarioRepository.findAll()
                .stream()
                .map(inventarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> listarHabilitados() {
        return inventarioRepository.findAll()
                .stream()
                .filter(Inventario::getHabilitado)
                .map(inventarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public InventarioResponseDTO actualizar(Integer id, InventarioRequestDTO request) {
        Inventario entidad = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", id));

        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        TipoInventario tipoInventario = tipoInventarioRepository.findById(request.getIdTipoInventario())
                .orElseThrow(() -> new ResourceNotFoundException("TipoInventario", request.getIdTipoInventario()));

        inventarioMapper.updateEntity(entidad, request);
        entidad.setArea(area);
        entidad.setTipoInventario(tipoInventario);

        Inventario actualizado = inventarioRepository.save(entidad);
        return inventarioMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        Inventario entidad = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        inventarioRepository.save(entidad);
    }
}
