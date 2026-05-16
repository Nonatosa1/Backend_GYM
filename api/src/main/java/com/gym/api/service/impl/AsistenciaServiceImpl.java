package com.gym.api.service.impl;

import com.gym.api.dto.request.AsistenciaRequestDTO;
import com.gym.api.dto.response.AsistenciaResponseDTO;
import com.gym.api.entity.Asistencia;
import com.gym.api.entity.Clase;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.AsistenciaMapper;
import com.gym.api.repository.AsistenciaRepository;
import com.gym.api.repository.ClaseRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final ClaseRepository claseRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsistenciaMapper asistenciaMapper;

    @Override
    public AsistenciaResponseDTO crear(AsistenciaRequestDTO request) {
        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Asistencia entidad = asistenciaMapper.toEntity(request);
        entidad.setClase(clase);
        entidad.setUsuario(usuario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Asistencia guardada = asistenciaRepository.save(entidad);
        return asistenciaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AsistenciaResponseDTO consultarPorId(Integer id) {
        Asistencia entidad = asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia", id));
        return asistenciaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDTO> listarTodos() {
        return asistenciaRepository.findAll()
                .stream()
                .map(asistenciaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDTO> listarHabilitados() {
        return asistenciaRepository.findAll()
                .stream()
                .filter(Asistencia::getHabilitado)
                .map(asistenciaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AsistenciaResponseDTO actualizar(Integer id, AsistenciaRequestDTO request) {
        Asistencia entidad = asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia", id));

        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        asistenciaMapper.updateEntity(entidad, request);
        entidad.setClase(clase);
        entidad.setUsuario(usuario);

        Asistencia actualizada = asistenciaRepository.save(entidad);
        return asistenciaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Asistencia entidad = asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        asistenciaRepository.save(entidad);
    }
}
