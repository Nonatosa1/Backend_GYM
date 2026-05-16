package com.gym.api.util;

import com.gym.api.dto.request.AsistenciaRequestDTO;
import com.gym.api.dto.response.AsistenciaResponseDTO;
import com.gym.api.entity.Asistencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsistenciaMapper {

    private final ClaseMapper claseMapper;
    private final UsuarioMapper usuarioMapper;

    public Asistencia toEntity(AsistenciaRequestDTO dto) {
        Asistencia entidad = new Asistencia();
        entidad.setFecha(dto.getFecha());
        entidad.setAsistio(dto.getAsistio());
        return entidad;
    }

    public void updateEntity(Asistencia entidad, AsistenciaRequestDTO dto) {
        entidad.setFecha(dto.getFecha());
        entidad.setAsistio(dto.getAsistio());
    }

    public AsistenciaResponseDTO toResponseDTO(Asistencia entidad) {
        return AsistenciaResponseDTO.builder()
                .idAsistencia(entidad.getIdAsistencia())
                .clase(entidad.getClase() != null ? claseMapper.toResponseDTO(entidad.getClase()) : null)
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .fecha(entidad.getFecha())
                .asistio(entidad.getAsistio())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
