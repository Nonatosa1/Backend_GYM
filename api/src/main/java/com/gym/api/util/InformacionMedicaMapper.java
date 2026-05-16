package com.gym.api.util;

import com.gym.api.dto.request.InformacionMedicaRequestDTO;
import com.gym.api.dto.response.InformacionMedicaResponseDTO;
import com.gym.api.entity.InformacionMedica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InformacionMedicaMapper {

    private final UsuarioMapper usuarioMapper;

    public InformacionMedica toEntity(InformacionMedicaRequestDTO dto) {
        InformacionMedica entidad = new InformacionMedica();
        entidad.setTipoSangre(dto.getTipoSangre());
        entidad.setAlergias(dto.getAlergias());
        entidad.setCondicionesDeSalud(dto.getCondicionesDeSalud());
        entidad.setTelefonoEmergencia(dto.getTelefonoEmergencia());
        return entidad;
    }

    public void updateEntity(InformacionMedica entidad, InformacionMedicaRequestDTO dto) {
        entidad.setTipoSangre(dto.getTipoSangre());
        entidad.setAlergias(dto.getAlergias());
        entidad.setCondicionesDeSalud(dto.getCondicionesDeSalud());
        entidad.setTelefonoEmergencia(dto.getTelefonoEmergencia());
    }

    public InformacionMedicaResponseDTO toResponseDTO(InformacionMedica entidad) {
        return InformacionMedicaResponseDTO.builder()
                .idInformacionMedica(entidad.getIdInformacionMedica())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .tipoSangre(entidad.getTipoSangre())
                .alergias(entidad.getAlergias())
                .condicionesDeSalud(entidad.getCondicionesDeSalud())
                .telefonoEmergencia(entidad.getTelefonoEmergencia())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
