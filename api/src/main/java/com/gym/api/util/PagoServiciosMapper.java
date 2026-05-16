package com.gym.api.util;

import com.gym.api.dto.request.PagoServiciosRequestDTO;
import com.gym.api.dto.response.PagoServiciosResponseDTO;
import com.gym.api.entity.PagoServicios;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad PagoServicios y
 * sus DTOs.
 *
 * Delega en RentaServiciosMapper para convertir la renta asociada y en
 * MetodoPagoMapper para convertir el metodo de pago utilizado. Notese que
 * el campo total (BigDecimal) si se copia directamente en el toEntity,
 * porque no requiere resolucion adicional a diferencia de las relaciones.
 */
@Component
@RequiredArgsConstructor
public class PagoServiciosMapper {

    private final RentaServiciosMapper rentaServiciosMapper;
    private final MetodoPagoMapper metodoPagoMapper;

    public PagoServicios toEntity(PagoServiciosRequestDTO dto) {
        PagoServicios entidad = new PagoServicios();
        entidad.setTotal(dto.getTotal());
        return entidad;
    }

    public void updateEntity(PagoServicios entidad, PagoServiciosRequestDTO dto) {
        entidad.setTotal(dto.getTotal());
    }

    public PagoServiciosResponseDTO toResponseDTO(PagoServicios entidad) {
        return PagoServiciosResponseDTO.builder()
                .idPagoServicio(entidad.getIdPagoServicio())
                .rentaServicios(entidad.getRentaServicios() != null ? rentaServiciosMapper.toResponseDTO(entidad.getRentaServicios()) : null)
                .total(entidad.getTotal())
                .metodoPago(entidad.getMetodoPago() != null ? metodoPagoMapper.toResponseDTO(entidad.getMetodoPago()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
