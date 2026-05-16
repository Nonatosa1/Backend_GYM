package com.gym.api.service.impl;

import com.gym.api.dto.request.ClaseRequestDTO;
import com.gym.api.dto.response.ClaseResponseDTO;
import com.gym.api.entity.Actividad;
import com.gym.api.entity.Clase;
import com.gym.api.entity.Persona;
import com.gym.api.entity.TipoClase;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.ClaseMapper;
import com.gym.api.repository.ActividadRepository;
import com.gym.api.repository.ClaseRepository;
import com.gym.api.repository.PersonaRepository;
import com.gym.api.repository.TipoClaseRepository;
import com.gym.api.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Implementacion del contrato ClaseService.
 *
 * Coordina la resolucion de tres relaciones simultaneas (persona responsable,
 * tipo de clase y actividad), aplica una validacion semantica de horarios
 * (la hora de termino debe ser posterior a la de inicio), y gestiona la
 * auditoria automatica como el resto de los servicios del proyecto.
 *
 * Notese que la validacion de horarios es analoga a la validacion de fechas
 * que ya conoces de PermisoAcceso y Agenda. La unica diferencia es que aqui
 * trabajamos con LocalTime en lugar de LocalDateTime, pero la API es identica:
 * ambos tipos tienen un metodo isAfter que permite comparar valores
 * directamente.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final ClaseRepository claseRepository;
    private final PersonaRepository personaRepository;
    private final TipoClaseRepository tipoClaseRepository;
    private final ActividadRepository actividadRepository;
    private final ClaseMapper claseMapper;

    @Override
    public ClaseResponseDTO crear(ClaseRequestDTO request) {
        // Validacion semantica de horarios: la hora de termino debe ser
        // posterior a la de inicio. Es la validacion mas barata, asi que
        // la hacemos primero siguiendo el principio "fail fast".
        validarHorarios(request.getHoraInicio(), request.getHoraTermino());

        Persona personaResponsable = personaRepository.findById(request.getIdPersonaResponsable())
                .orElseThrow(() -> new ResourceNotFoundException("Persona", request.getIdPersonaResponsable()));

        TipoClase tipoClase = tipoClaseRepository.findById(request.getIdTipoClase())
                .orElseThrow(() -> new ResourceNotFoundException("TipoClase", request.getIdTipoClase()));

        Actividad actividad = actividadRepository.findById(request.getIdActividad())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", request.getIdActividad()));

        Clase entidad = claseMapper.toEntity(request);
        entidad.setPersonaResponsable(personaResponsable);
        entidad.setTipoClase(tipoClase);
        entidad.setActividad(actividad);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Clase guardada = claseRepository.save(entidad);
        return claseMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public ClaseResponseDTO consultarPorId(Integer id) {
        Clase entidad = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));
        return claseMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseResponseDTO> listarTodos() {
        return claseRepository.findAll()
                .stream()
                .map(claseMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClaseResponseDTO> listarHabilitados() {
        return claseRepository.findAll()
                .stream()
                .filter(Clase::getHabilitado)
                .map(claseMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ClaseResponseDTO actualizar(Integer id, ClaseRequestDTO request) {
        validarHorarios(request.getHoraInicio(), request.getHoraTermino());

        Clase entidad = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        Persona personaResponsable = personaRepository.findById(request.getIdPersonaResponsable())
                .orElseThrow(() -> new ResourceNotFoundException("Persona", request.getIdPersonaResponsable()));

        TipoClase tipoClase = tipoClaseRepository.findById(request.getIdTipoClase())
                .orElseThrow(() -> new ResourceNotFoundException("TipoClase", request.getIdTipoClase()));

        Actividad actividad = actividadRepository.findById(request.getIdActividad())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", request.getIdActividad()));

        claseMapper.updateEntity(entidad, request);
        entidad.setPersonaResponsable(personaResponsable);
        entidad.setTipoClase(tipoClase);
        entidad.setActividad(actividad);

        Clase actualizada = claseRepository.save(entidad);
        return claseMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Clase entidad = claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        claseRepository.save(entidad);
    }

    /**
     * Verifica que la hora de termino sea posterior a la hora de inicio.
     * Esta validacion es analoga a la validarFechas de otros servicios,
     * pero aplicada a LocalTime en lugar de LocalDateTime.
     */
    private void validarHorarios(LocalTime horaInicio, LocalTime horaTermino) {
        if (!horaTermino.isAfter(horaInicio)) {
            throw new BusinessException("La hora de termino debe ser posterior a la hora de inicio");
        }
    }
}
