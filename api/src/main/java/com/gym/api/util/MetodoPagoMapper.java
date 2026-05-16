package com.gym.api.util;

import com.gym.api.dto.request.MetodoPagoRequestDTO;
import com.gym.api.dto.response.MetodoPagoResponseDTO;
import com.gym.api.entity.MetodoPago;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagoMapper {

    public MetodoPago toEntity(MetodoPagoRequestDTO dto) {
        MetodoPago entidad = new MetodoPago();
        entidad.setMetodoPago(dto.getMetodoPago());
        entidad.setDescripcion(dto.getDescripcion());
        return entidad;
    }

    public void updateEntity(MetodoPago entidad, MetodoPagoRequestDTO dto) {
        entidad.setMetodoPago(dto.getMetodoPago());
        entidad.setDescripcion(dto.getDescripcion());
    }

    public MetodoPagoResponseDTO toResponseDTO(MetodoPago entidad) {
        return MetodoPagoResponseDTO.builder()
                .idMetodoPago(entidad.getIdMetodoPago())
                .metodoPago(entidad.getMetodoPago())
                .descripcion(entidad.getDescripcion())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
