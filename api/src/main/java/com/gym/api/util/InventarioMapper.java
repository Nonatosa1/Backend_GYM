package com.gym.api.util;

import com.gym.api.dto.request.InventarioRequestDTO;
import com.gym.api.dto.response.InventarioResponseDTO;
import com.gym.api.entity.Inventario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Inventario y
 * sus DTOs.
 *
 * Delega en AreaMapper y TipoInventarioMapper para convertir las entidades
 * relacionadas. Como en los mappers anteriores, las relaciones no se
 * establecen en toEntity ni en updateEntity. Esa responsabilidad
 * pertenece al servicio.
 */
@Component
@RequiredArgsConstructor
public class InventarioMapper {

    private final AreaMapper areaMapper;
    private final TipoInventarioMapper tipoInventarioMapper;

    public Inventario toEntity(InventarioRequestDTO dto) {
        Inventario entidad = new Inventario();
        entidad.setInventario(dto.getInventario());
        return entidad;
    }

    public void updateEntity(Inventario entidad, InventarioRequestDTO dto) {
        entidad.setInventario(dto.getInventario());
    }

    public InventarioResponseDTO toResponseDTO(Inventario entidad) {
        return InventarioResponseDTO.builder()
                .idInventario(entidad.getIdInventario())
                .inventario(entidad.getInventario())
                .area(entidad.getArea() != null ? areaMapper.toResponseDTO(entidad.getArea()) : null)
                .tipoInventario(entidad.getTipoInventario() != null ? tipoInventarioMapper.toResponseDTO(entidad.getTipoInventario()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
