package com.gym.api.util;

import com.gym.api.dto.request.PagoRequestDTO;
import com.gym.api.dto.response.PagoResponseDTO;
import com.gym.api.entity.Pago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Pago y sus DTOs.
 *
 * Delega en InscripcionMapper para convertir la inscripcion asociada. El
 * monto total se copia directamente porque BigDecimal es un tipo simple
 * que no requiere conversion adicional.
 */
@Component
@RequiredArgsConstructor
public class PagoMapper {

    private final InscripcionMapper inscripcionMapper;

    public Pago toEntity(PagoRequestDTO dto) {
        Pago entidad = new Pago();
        entidad.setMontoTotal(dto.getMontoTotal());
        return entidad;
    }

    public void updateEntity(Pago entidad, PagoRequestDTO dto) {
        entidad.setMontoTotal(dto.getMontoTotal());
    }

    public PagoResponseDTO toResponseDTO(Pago entidad) {
        return PagoResponseDTO.builder()
                .idPago(entidad.getIdPago())
                .inscripcion(entidad.getInscripcion() != null ? inscripcionMapper.toResponseDTO(entidad.getInscripcion()) : null)
                .montoTotal(entidad.getMontoTotal())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
