package com.gym.api.util;

import com.gym.api.dto.request.RentaServiciosRequestDTO;
import com.gym.api.dto.response.RentaServiciosResponseDTO;
import com.gym.api.entity.RentaServicios;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad RentaServicios
 * y sus DTOs.
 *
 * Sigue el patron de composicion de mappers que ya conoces. Delega en
 * UsuarioMapper para convertir el usuario que renta y en InventarioMapper
 * para convertir el equipo rentado. Las relaciones no se establecen en
 * los metodos toEntity ni updateEntity porque esa responsabilidad
 * pertenece al servicio.
 */
@Component
@RequiredArgsConstructor
public class RentaServiciosMapper {

    private final UsuarioMapper usuarioMapper;
    private final InventarioMapper inventarioMapper;

    public RentaServicios toEntity(RentaServiciosRequestDTO dto) {
        RentaServicios entidad = new RentaServicios();
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaVence(dto.getFechaVence());
        return entidad;
    }

    public void updateEntity(RentaServicios entidad, RentaServiciosRequestDTO dto) {
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaVence(dto.getFechaVence());
    }

    public RentaServiciosResponseDTO toResponseDTO(RentaServicios entidad) {
        return RentaServiciosResponseDTO.builder()
                .idRentaServicio(entidad.getIdRentaServicio())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .inventario(entidad.getInventario() != null ? inventarioMapper.toResponseDTO(entidad.getInventario()) : null)
                .fechaInicio(entidad.getFechaInicio())
                .fechaVence(entidad.getFechaVence())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
