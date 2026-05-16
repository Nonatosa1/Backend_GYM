package com.gym.api.util;

import com.gym.api.dto.request.EspacioFisicoRequestDTO;
import com.gym.api.dto.response.EspacioFisicoResponseDTO;
import com.gym.api.entity.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad EspacioFisico y
 * sus DTOs.
 *
 * Sigue el patron del UsuarioMapper de delegar en otros mappers cuando
 * necesita convertir entidades relacionadas. En este caso delega en
 * AreaMapper para convertir el area asociada al espacio fisico, lo cual
 * mantiene la conversion de cada entidad encapsulada en su propio mapper.
 *
 * Igual que con Usuario, el metodo toEntity solo copia los campos simples
 * del DTO. La resolucion de la relacion con Area (encontrar la entidad
 * Area a partir del idArea) es responsabilidad del servicio.
 */
@Component
@RequiredArgsConstructor
public class EspacioFisicoMapper {

    private final AreaMapper areaMapper;

    public EspacioFisico toEntity(EspacioFisicoRequestDTO dto) {
        EspacioFisico entidad = new EspacioFisico();
        entidad.setEspacioFisico(dto.getEspacioFisico());
        return entidad;
    }

    public void updateEntity(EspacioFisico entidad, EspacioFisicoRequestDTO dto) {
        entidad.setEspacioFisico(dto.getEspacioFisico());
    }

    public EspacioFisicoResponseDTO toResponseDTO(EspacioFisico entidad) {
        return EspacioFisicoResponseDTO.builder()
                .idEspacioFisico(entidad.getIdEspacioFisico())
                .espacioFisico(entidad.getEspacioFisico())
                .area(entidad.getArea() != null ? areaMapper.toResponseDTO(entidad.getArea()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
