package com.gym.api.util;

import com.gym.api.dto.request.ClaseMembresiaRequestDTO;
import com.gym.api.dto.response.ClaseMembresiaResponseDTO;
import com.gym.api.entity.ClaseMembresia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaseMembresiaMapper {

    private final ClaseMapper claseMapper;
    private final MembresiaMapper membresiaMapper;

    public ClaseMembresia toEntity(ClaseMembresiaRequestDTO dto) {
        return new ClaseMembresia();
    }

    public void updateEntity(ClaseMembresia entidad, ClaseMembresiaRequestDTO dto) {
        // No hay campos propios que actualizar mas alla de las relaciones.
    }

    public ClaseMembresiaResponseDTO toResponseDTO(ClaseMembresia entidad) {
        return ClaseMembresiaResponseDTO.builder()
                .idClaseMembresia(entidad.getIdClaseMembresia())
                .clase(entidad.getClase() != null ? claseMapper.toResponseDTO(entidad.getClase()) : null)
                .membresia(entidad.getMembresia() != null ? membresiaMapper.toResponseDTO(entidad.getMembresia()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
