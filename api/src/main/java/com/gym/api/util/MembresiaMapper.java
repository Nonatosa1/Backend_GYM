package com.gym.api.util;

import com.gym.api.dto.request.MembresiaRequestDTO;
import com.gym.api.dto.response.MembresiaResponseDTO;
import com.gym.api.entity.Membresia;
import org.springframework.stereotype.Component;

@Component
public class MembresiaMapper {

    public Membresia toEntity(MembresiaRequestDTO dto) {
        Membresia entidad = new Membresia();
        entidad.setMembresia(dto.getMembresia());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setPrecio(dto.getPrecio());
        entidad.setDuracionDias(dto.getDuracionDias());
        entidad.setIncluyeClases(dto.getIncluyeClases());
        return entidad;
    }

    public void updateEntity(Membresia entidad, MembresiaRequestDTO dto) {
        entidad.setMembresia(dto.getMembresia());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setPrecio(dto.getPrecio());
        entidad.setDuracionDias(dto.getDuracionDias());
        entidad.setIncluyeClases(dto.getIncluyeClases());
    }

    public MembresiaResponseDTO toResponseDTO(Membresia entidad) {
        return MembresiaResponseDTO.builder()
                .idMembresia(entidad.getIdMembresia())
                .membresia(entidad.getMembresia())
                .descripcion(entidad.getDescripcion())
                .precio(entidad.getPrecio())
                .duracionDias(entidad.getDuracionDias())
                .incluyeClases(entidad.getIncluyeClases())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
