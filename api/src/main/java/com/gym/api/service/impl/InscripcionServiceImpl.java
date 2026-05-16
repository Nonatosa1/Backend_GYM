package com.gym.api.service.impl;

import com.gym.api.dto.request.InscripcionRequestDTO;
import com.gym.api.dto.response.InscripcionResponseDTO;
import com.gym.api.entity.Inscripcion;
import com.gym.api.entity.Membresia;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.InscripcionMapper;
import com.gym.api.repository.InscripcionRepository;
import com.gym.api.repository.MembresiaRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato InscripcionService.
 *
 * Aplica los patrones que ya conoces de servicios con multiples
 * relaciones: resolucion de cada referencia por ID, validacion semantica
 * de fechas siguiendo el principio "fail fast", y auditoria automatica.
 *
 * Notese que la validacion de fechas usa el metodo isAfter de LocalDate,
 * que funciona analogamente al de LocalDateTime que usamos en otros
 * servicios. La unica diferencia es que aqui las fechas no tienen
 * componente horario, lo cual es apropiado para inscripciones cuya
 * vigencia se mide en dias completos.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final MembresiaRepository membresiaRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscripcionMapper inscripcionMapper;

    @Override
    public InscripcionResponseDTO crear(InscripcionRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaVencimiento());

        Membresia membresia = membresiaRepository.findById(request.getIdMembresia())
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", request.getIdMembresia()));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Inscripcion entidad = inscripcionMapper.toEntity(request);
        entidad.setMembresia(membresia);
        entidad.setUsuario(usuario);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Inscripcion guardada = inscripcionRepository.save(entidad);
        return inscripcionMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponseDTO consultarPorId(Integer id) {
        Inscripcion entidad = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", id));
        return inscripcionMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponseDTO> listarTodos() {
        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponseDTO> listarHabilitados() {
        return inscripcionRepository.findAll()
                .stream()
                .filter(Inscripcion::getHabilitado)
                .map(inscripcionMapper::toResponseDTO)
                .toList();
    }

    @Override
    public InscripcionResponseDTO actualizar(Integer id, InscripcionRequestDTO request) {
        validarFechas(request.getFechaInicio(), request.getFechaVencimiento());

        Inscripcion entidad = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", id));

        Membresia membresia = membresiaRepository.findById(request.getIdMembresia())
                .orElseThrow(() -> new ResourceNotFoundException("Membresia", request.getIdMembresia()));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        inscripcionMapper.updateEntity(entidad, request);
        entidad.setMembresia(membresia);
        entidad.setUsuario(usuario);

        Inscripcion actualizada = inscripcionRepository.save(entidad);
        return inscripcionMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Inscripcion entidad = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        inscripcionRepository.save(entidad);
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaVencimiento) {
        if (!fechaVencimiento.isAfter(fechaInicio)) {
            throw new BusinessException("La fecha de vencimiento debe ser posterior a la fecha de inicio");
        }
    }
}
