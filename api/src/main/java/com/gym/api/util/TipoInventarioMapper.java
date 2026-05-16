package com.gym.api.util;

import com.gym.api.dto.request.TipoInventarioRequestDTO;
import com.gym.api.dto.response.TipoInventarioResponseDTO;
import com.gym.api.entity.TipoInventario;
import org.springframework.stereotype.Component;

@Component
public class TipoInventarioMapper {

    public TipoInventario toEntity(TipoInventarioRequestDTO dto) {
        TipoInventario entidad = new TipoInventario();
        entidad.setTipoEquipo(dto.getTipoEquipo());
        return entidad;
    }

    public void updateEntity(TipoInventario entidad, TipoInventarioRequestDTO dto) {
        entidad.setTipoEquipo(dto.getTipoEquipo());
    }

    public TipoInventarioResponseDTO toResponseDTO(TipoInventario entidad) {
        return TipoInventarioResponseDTO.builder()
                .idTipoInventario(entidad.getIdTipoInventario())
                .tipoEquipo(entidad.getTipoEquipo())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
