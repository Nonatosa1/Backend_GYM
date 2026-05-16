package com.gym.api.service.impl;

import com.gym.api.dto.request.InformacionMedicaRequestDTO;
import com.gym.api.dto.response.InformacionMedicaResponseDTO;
import com.gym.api.entity.InformacionMedica;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.InformacionMedicaMapper;
import com.gym.api.repository.InformacionMedicaRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.InformacionMedicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato InformacionMedicaService.
 *
 * La logica es directa: resolver la relacion con Usuario, aplicar las
 * validaciones declarativas del DTO (incluyendo la del patron del tipo
 * sanguineo), construir la entidad y persistir.
 *
 * En un sistema mas maduro, este servicio deberia tener controles
 * adicionales como verificar que el usuario que realiza la consulta
 * tenga permisos para acceder a estos datos, registrar el acceso en un
 * log de auditoria, y posiblemente cifrar los datos sensibles antes de
 * persistir. Estos refinamientos quedan pendientes para futuras
 * versiones del proyecto.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InformacionMedicaServiceImpl implements InformacionMedicaService {

    private final InformacionMedicaRepository informacionMedicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final InformacionMedicaMapper informacionMedicaMapper;

    @Override
    public InformacionMedicaResponseDTO crear(InformacionMedicaRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        InformacionMedica entidad = informacionMedicaMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        InformacionMedica guardada = informacionMedicaRepository.save(entidad);
        return informacionMedicaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionMedicaResponseDTO consultarPorId(Integer id) {
        InformacionMedica entidad = informacionMedicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InformacionMedica", id));
        return informacionMedicaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InformacionMedicaResponseDTO> listarTodos() {
        return informacionMedicaRepository.findAll()
                .stream()
                .map(informacionMedicaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InformacionMedicaResponseDTO> listarHabilitados() {
        return informacionMedicaRepository.findAll()
                .stream()
                .filter(InformacionMedica::getHabilitado)
                .map(informacionMedicaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public InformacionMedicaResponseDTO actualizar(Integer id, InformacionMedicaRequestDTO request) {
        InformacionMedica entidad = informacionMedicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InformacionMedica", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        informacionMedicaMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);

        InformacionMedica actualizada = informacionMedicaRepository.save(entidad);
        return informacionMedicaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        InformacionMedica entidad = informacionMedicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InformacionMedica", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        informacionMedicaRepository.save(entidad);
    }
}
