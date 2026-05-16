package com.gym.api.util;

import com.gym.api.dto.request.DiaRequestDTO;
import com.gym.api.dto.response.DiaResponseDTO;
import com.gym.api.entity.Dia;
import org.springframework.stereotype.Component;

@Component
public class DiaMapper {

    public Dia toEntity(DiaRequestDTO dto) {
        Dia entidad = new Dia();
        entidad.setDia(dto.getDia());
        return entidad;
    }

    public void updateEntity(Dia entidad, DiaRequestDTO dto) {
        entidad.setDia(dto.getDia());
    }

    public DiaResponseDTO toResponseDTO(Dia entidad) {
        return DiaResponseDTO.builder()
                .idDia(entidad.getIdDia())
                .dia(entidad.getDia())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
