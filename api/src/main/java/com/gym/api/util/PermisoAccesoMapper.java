package com.gym.api.util;

import com.gym.api.dto.request.PermisoAccesoRequestDTO;
import com.gym.api.dto.response.PermisoAccesoResponseDTO;
import com.gym.api.entity.PermisoAcceso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad PermisoAcceso y
 * sus DTOs.
 *
 * Delega en UsuarioMapper y AreaMapper para convertir las entidades
 * relacionadas, siguiendo el principio de que cada mapper conoce solo su
 * propia entidad y se apoya en otros mappers cuando necesita
 * conversiones que no le competen directamente. Esto evita duplicacion y
 * mantiene la responsabilidad de cada componente bien delimitada.
 *
 * Las relaciones (usuario y area) no se establecen en toEntity ni en
 * updateEntity. Esa responsabilidad pertenece al servicio, que debe
 * resolver los IDs del DTO buscando las entidades correspondientes en la
 * base de datos antes de invocar al mapper o despues de obtener la
 * entidad base.
 */
@Component
@RequiredArgsConstructor
public class PermisoAccesoMapper {

    private final UsuarioMapper usuarioMapper;
    private final AreaMapper areaMapper;

    public PermisoAcceso toEntity(PermisoAccesoRequestDTO dto) {
        PermisoAcceso entidad = new PermisoAcceso();
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        return entidad;
    }

    public void updateEntity(PermisoAcceso entidad, PermisoAccesoRequestDTO dto) {
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
    }

    public PermisoAccesoResponseDTO toResponseDTO(PermisoAcceso entidad) {
        return PermisoAccesoResponseDTO.builder()
                .idPermisoAcceso(entidad.getIdPermisoAcceso())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .area(entidad.getArea() != null ? areaMapper.toResponseDTO(entidad.getArea()) : null)
                .fechaInicio(entidad.getFechaInicio())
                .fechaFin(entidad.getFechaFin())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
