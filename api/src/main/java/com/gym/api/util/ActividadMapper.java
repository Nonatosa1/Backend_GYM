package com.gym.api.util;

import com.gym.api.dto.request.ActividadRequestDTO;
import com.gym.api.dto.response.ActividadResponseDTO;
import com.gym.api.entity.Actividad;
import org.springframework.stereotype.Component;

@Component
public class ActividadMapper {

    public Actividad toEntity(ActividadRequestDTO dto) {
        Actividad entidad = new Actividad();
        entidad.setActividad(dto.getActividad());
        return entidad;
    }

    public void updateEntity(Actividad entidad, ActividadRequestDTO dto) {
        entidad.setActividad(dto.getActividad());
    }

    public ActividadResponseDTO toResponseDTO(Actividad entidad) {
        return ActividadResponseDTO.builder()
                .idActividad(entidad.getIdActividad())
                .actividad(entidad.getActividad())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
