package com.gym.api.service.impl;

import com.gym.api.dto.request.EspacioFisicoRequestDTO;
import com.gym.api.dto.response.EspacioFisicoResponseDTO;
import com.gym.api.entity.Area;
import com.gym.api.entity.EspacioFisico;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.EspacioFisicoMapper;
import com.gym.api.repository.AreaRepository;
import com.gym.api.repository.EspacioFisicoRepository;
import com.gym.api.service.EspacioFisicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato EspacioFisicoService.
 *
 * Sigue el patron establecido en UsuarioServiceImpl para resolver la
 * relacion con otra entidad a partir de su ID. La idea central es que el
 * cliente envia el idArea, el servicio busca la entidad Area en la base
 * de datos verificando que exista, y solo entonces procede a construir y
 * persistir el nuevo espacio fisico.
 *
 * Si el area indicada no existe, el servicio lanza
 * ResourceNotFoundException con un mensaje claro, y el manejador global
 * de excepciones lo convierte en una respuesta HTTP 404 estructurada.
 * Este es el patron "fail fast" del que ya hemos hablado.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EspacioFisicoServiceImpl implements EspacioFisicoService {

    private final EspacioFisicoRepository espacioFisicoRepository;
    private final AreaRepository areaRepository;
    private final EspacioFisicoMapper espacioFisicoMapper;

    @Override
    public EspacioFisicoResponseDTO crear(EspacioFisicoRequestDTO request) {
        // Resolvemos la relacion con Area antes de hacer cualquier otra cosa.
        // Si no existe, fallamos rapido con un error claro.
        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        EspacioFisico entidad = espacioFisicoMapper.toEntity(request);
        entidad.setArea(area);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        EspacioFisico guardado = espacioFisicoRepository.save(entidad);
        return espacioFisicoMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public EspacioFisicoResponseDTO consultarPorId(Short id) {
        EspacioFisico entidad = espacioFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EspacioFisico", id));
        return espacioFisicoMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioFisicoResponseDTO> listarTodos() {
        return espacioFisicoRepository.findAll()
                .stream()
                .map(espacioFisicoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioFisicoResponseDTO> listarHabilitados() {
        return espacioFisicoRepository.findAll()
                .stream()
                .filter(EspacioFisico::getHabilitado)
                .map(espacioFisicoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public EspacioFisicoResponseDTO actualizar(Short id, EspacioFisicoRequestDTO request) {
        EspacioFisico entidad = espacioFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EspacioFisico", id));

        // El area podria haber cambiado en la actualizacion, asi que la
        // resolvemos de nuevo. Si el cliente envia el mismo idArea que ya
        // tenia, la operacion sigue siendo correcta porque encontramos el
        // mismo area.
        Area area = areaRepository.findById(request.getIdArea())
                .orElseThrow(() -> new ResourceNotFoundException("Area", request.getIdArea()));

        espacioFisicoMapper.updateEntity(entidad, request);
        entidad.setArea(area);

        EspacioFisico actualizado = espacioFisicoRepository.save(entidad);
        return espacioFisicoMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Short id) {
        EspacioFisico entidad = espacioFisicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EspacioFisico", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        espacioFisicoRepository.save(entidad);
    }
}
