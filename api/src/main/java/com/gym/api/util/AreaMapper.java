package com.gym.api.util;

import com.gym.api.dto.request.AreaRequestDTO;
import com.gym.api.dto.response.AreaResponseDTO;
import com.gym.api.entity.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {

    public Area toEntity(AreaRequestDTO dto) {
        Area entidad = new Area();
        entidad.setArea(dto.getArea());
        return entidad;
    }

    public void updateEntity(Area entidad, AreaRequestDTO dto) {
        entidad.setArea(dto.getArea());
    }

    public AreaResponseDTO toResponseDTO(Area entidad) {
        return AreaResponseDTO.builder()
                .idArea(entidad.getIdArea())
                .area(entidad.getArea())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
