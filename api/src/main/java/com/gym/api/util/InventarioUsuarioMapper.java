package com.gym.api.util;

import com.gym.api.dto.request.InventarioUsuarioRequestDTO;
import com.gym.api.dto.response.InventarioUsuarioResponseDTO;
import com.gym.api.entity.InventarioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad InventarioUsuario
 * y sus DTOs.
 *
 * Delega en UsuarioMapper para convertir el usuario asociado y en
 * InventarioMapper para convertir el equipo de inventario asignado. Este
 * es otro ejemplo del patron de composicion de mappers que ya hemos visto
 * en el resto del proyecto: cada mapper conoce solo su propia entidad y
 * se apoya en otros mappers cuando necesita convertir entidades
 * relacionadas.
 */
@Component
@RequiredArgsConstructor
public class InventarioUsuarioMapper {

    private final UsuarioMapper usuarioMapper;
    private final InventarioMapper inventarioMapper;

    public InventarioUsuario toEntity(InventarioUsuarioRequestDTO dto) {
        InventarioUsuario entidad = new InventarioUsuario();
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        return entidad;
    }

    public void updateEntity(InventarioUsuario entidad, InventarioUsuarioRequestDTO dto) {
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
    }

    public InventarioUsuarioResponseDTO toResponseDTO(InventarioUsuario entidad) {
        return InventarioUsuarioResponseDTO.builder()
                .idInventarioUsuario(entidad.getIdInventarioUsuario())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .inventario(entidad.getInventario() != null ? inventarioMapper.toResponseDTO(entidad.getInventario()) : null)
                .fechaInicio(entidad.getFechaInicio())
                .fechaFin(entidad.getFechaFin())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
