package com.gym.api.util;

import com.gym.api.dto.request.InscripcionRequestDTO;
import com.gym.api.dto.response.InscripcionResponseDTO;
import com.gym.api.entity.Inscripcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Inscripcion y
 * sus DTOs.
 *
 * Delega en MembresiaMapper para convertir la membresia adquirida y en
 * UsuarioMapper para convertir el usuario inscrito. Las relaciones no se
 * establecen en los metodos toEntity ni updateEntity porque esa
 * responsabilidad pertenece al servicio.
 */
@Component
@RequiredArgsConstructor
public class InscripcionMapper {

    private final MembresiaMapper membresiaMapper;
    private final UsuarioMapper usuarioMapper;

    public Inscripcion toEntity(InscripcionRequestDTO dto) {
        Inscripcion entidad = new Inscripcion();
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaVencimiento(dto.getFechaVencimiento());
        return entidad;
    }

    public void updateEntity(Inscripcion entidad, InscripcionRequestDTO dto) {
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaVencimiento(dto.getFechaVencimiento());
    }

    public InscripcionResponseDTO toResponseDTO(Inscripcion entidad) {
        return InscripcionResponseDTO.builder()
                .idInscripcion(entidad.getIdInscripcion())
                .membresia(entidad.getMembresia() != null ? membresiaMapper.toResponseDTO(entidad.getMembresia()) : null)
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .fechaInicio(entidad.getFechaInicio())
                .fechaVencimiento(entidad.getFechaVencimiento())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
