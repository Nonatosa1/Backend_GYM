package com.gym.api.util;

import com.gym.api.dto.request.TipoClaseRequestDTO;
import com.gym.api.dto.response.TipoClaseResponseDTO;
import com.gym.api.entity.TipoClase;
import org.springframework.stereotype.Component;

@Component
public class TipoClaseMapper {

    public TipoClase toEntity(TipoClaseRequestDTO dto) {
        TipoClase entidad = new TipoClase();
        entidad.setTipoClase(dto.getTipoClase());
        entidad.setDescripcion(dto.getDescripcion());
        return entidad;
    }

    public void updateEntity(TipoClase entidad, TipoClaseRequestDTO dto) {
        entidad.setTipoClase(dto.getTipoClase());
        entidad.setDescripcion(dto.getDescripcion());
    }

    public TipoClaseResponseDTO toResponseDTO(TipoClase entidad) {
        return TipoClaseResponseDTO.builder()
                .idTipoClase(entidad.getIdTipoClase())
                .tipoClase(entidad.getTipoClase())
                .descripcion(entidad.getDescripcion())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
