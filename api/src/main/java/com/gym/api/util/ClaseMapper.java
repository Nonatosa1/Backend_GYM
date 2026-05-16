package com.gym.api.util;

import com.gym.api.dto.request.ClaseRequestDTO;
import com.gym.api.dto.response.ClaseResponseDTO;
import com.gym.api.entity.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Clase y sus DTOs.
 *
 * Como el AgendaMapper del bloque anterior, este mapper delega en tres
 * mappers distintos: PersonaMapper para el instructor responsable,
 * TipoClaseMapper para el formato de la clase, y ActividadMapper para la
 * disciplina practicada. La composicion de mappers se mantiene como el
 * patron estandar del proyecto cuando una entidad tiene multiples
 * relaciones que conviene exponer en la respuesta.
 *
 * Las relaciones no se establecen en toEntity ni en updateEntity porque
 * esa responsabilidad pertenece al servicio, que debe resolver los IDs
 * del DTO buscando las entidades correspondientes en la base de datos.
 */
@Component
@RequiredArgsConstructor
public class ClaseMapper {

    private final PersonaMapper personaMapper;
    private final TipoClaseMapper tipoClaseMapper;
    private final ActividadMapper actividadMapper;

    public Clase toEntity(ClaseRequestDTO dto) {
        Clase entidad = new Clase();
        entidad.setNoMaxAlumnos(dto.getNoMaxAlumnos());
        entidad.setHoraInicio(dto.getHoraInicio());
        entidad.setHoraTermino(dto.getHoraTermino());
        return entidad;
    }

    public void updateEntity(Clase entidad, ClaseRequestDTO dto) {
        entidad.setNoMaxAlumnos(dto.getNoMaxAlumnos());
        entidad.setHoraInicio(dto.getHoraInicio());
        entidad.setHoraTermino(dto.getHoraTermino());
    }

    public ClaseResponseDTO toResponseDTO(Clase entidad) {
        return ClaseResponseDTO.builder()
                .idClase(entidad.getIdClase())
                .personaResponsable(entidad.getPersonaResponsable() != null ? personaMapper.toResponseDTO(entidad.getPersonaResponsable()) : null)
                .noMaxAlumnos(entidad.getNoMaxAlumnos())
                .horaInicio(entidad.getHoraInicio())
                .horaTermino(entidad.getHoraTermino())
                .tipoClase(entidad.getTipoClase() != null ? tipoClaseMapper.toResponseDTO(entidad.getTipoClase()) : null)
                .actividad(entidad.getActividad() != null ? actividadMapper.toResponseDTO(entidad.getActividad()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
