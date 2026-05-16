package com.gym.api.util;

import com.gym.api.dto.request.ClaseDiaRequestDTO;
import com.gym.api.dto.response.ClaseDiaResponseDTO;
import com.gym.api.entity.ClaseDia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad ClaseDia y sus DTOs.
 *
 * Como la entidad solo tiene las dos relaciones y los campos de auditoria,
 * los metodos toEntity y updateEntity son los mas minimalistas del
 * proyecto: practicamente no hacen nada porque toda la informacion
 * relevante son relaciones que se establecen en el servicio. Esto es
 * normal para tablas puente puras y refleja la simplicidad inherente de
 * la entidad.
 */
@Component
@RequiredArgsConstructor
public class ClaseDiaMapper {

    private final ClaseMapper claseMapper;
    private final DiaMapper diaMapper;

    public ClaseDia toEntity(ClaseDiaRequestDTO dto) {
        return new ClaseDia();
    }

    public void updateEntity(ClaseDia entidad, ClaseDiaRequestDTO dto) {
        // No hay campos propios que actualizar mas alla de las relaciones,
        // que son responsabilidad del servicio.
    }

    public ClaseDiaResponseDTO toResponseDTO(ClaseDia entidad) {
        return ClaseDiaResponseDTO.builder()
                .idClaseDia(entidad.getIdClaseDia())
                .clase(entidad.getClase() != null ? claseMapper.toResponseDTO(entidad.getClase()) : null)
                .dia(entidad.getDia() != null ? diaMapper.toResponseDTO(entidad.getDia()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
