package com.gym.api.util;

import com.gym.api.dto.request.DetallePagoRequestDTO;
import com.gym.api.dto.response.DetallePagoResponseDTO;
import com.gym.api.entity.DetallePago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetallePagoMapper {

    private final PagoMapper pagoMapper;
    private final MetodoPagoMapper metodoPagoMapper;

    public DetallePago toEntity(DetallePagoRequestDTO dto) {
        DetallePago entidad = new DetallePago();
        entidad.setMontoAbono(dto.getMontoAbono());
        entidad.setFechaAbono(dto.getFechaAbono());
        return entidad;
    }

    public void updateEntity(DetallePago entidad, DetallePagoRequestDTO dto) {
        entidad.setMontoAbono(dto.getMontoAbono());
        entidad.setFechaAbono(dto.getFechaAbono());
    }

    public DetallePagoResponseDTO toResponseDTO(DetallePago entidad) {
        return DetallePagoResponseDTO.builder()
                .idDetallePago(entidad.getIdDetallePago())
                .pago(entidad.getPago() != null ? pagoMapper.toResponseDTO(entidad.getPago()) : null)
                .montoAbono(entidad.getMontoAbono())
                .fechaAbono(entidad.getFechaAbono())
                .metodoPago(entidad.getMetodoPago() != null ? metodoPagoMapper.toResponseDTO(entidad.getMetodoPago()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
