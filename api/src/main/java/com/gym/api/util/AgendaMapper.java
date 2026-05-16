package com.gym.api.util;

import com.gym.api.dto.request.AgendaRequestDTO;
import com.gym.api.dto.response.AgendaResponseDTO;
import com.gym.api.entity.Agenda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Agenda y sus DTOs.
 *
 * Este es el mapper mas complejo del bloque porque delega en tres mappers
 * distintos para convertir las entidades relacionadas: UsuarioMapper para
 * el usuario que reservo, AreaMapper para el area donde se realizara la
 * actividad, y EspacioFisicoMapper para el espacio fisico concreto. Este
 * patron de composicion de mappers escala bien: cada mapper solo necesita
 * conocer los mappers de las entidades directamente relacionadas con la
 * suya, sin tener que conocer toda la cadena de transformaciones.
 *
 * Como en los mappers anteriores, las relaciones no se establecen en
 * toEntity ni en updateEntity. La resolucion de las tres relaciones a
 * partir de los IDs del DTO es responsabilidad del servicio.
 */
@Component
@RequiredArgsConstructor
public class AgendaMapper {

    private final UsuarioMapper usuarioMapper;
    private final AreaMapper areaMapper;
    private final EspacioFisicoMapper espacioFisicoMapper;

    public Agenda toEntity(AgendaRequestDTO dto) {
        Agenda entidad = new Agenda();
        entidad.setAgenda(dto.getAgenda());
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        return entidad;
    }

    public void updateEntity(Agenda entidad, AgendaRequestDTO dto) {
        entidad.setAgenda(dto.getAgenda());
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
    }

    public AgendaResponseDTO toResponseDTO(Agenda entidad) {
        return AgendaResponseDTO.builder()
                .idAgenda(entidad.getIdAgenda())
                .agenda(entidad.getAgenda())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .area(entidad.getArea() != null ? areaMapper.toResponseDTO(entidad.getArea()) : null)
                .espacioFisico(entidad.getEspacioFisico() != null ? espacioFisicoMapper.toResponseDTO(entidad.getEspacioFisico()) : null)
                .fechaInicio(entidad.getFechaInicio())
                .fechaFin(entidad.getFechaFin())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
