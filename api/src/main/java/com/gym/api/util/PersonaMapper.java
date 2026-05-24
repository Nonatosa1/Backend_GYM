package com.gym.api.util;

import com.gym.api.dto.request.PersonaRequestDTO;
import com.gym.api.dto.response.PersonaResponseDTO;
import com.gym.api.entity.Persona;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Persona y sus DTOs.
 *
 * Sigue el mismo patron que los mappers de los catalogos. La unica diferencia
 * es que tiene mas campos que mapear, pero estructuralmente el codigo es
 * identico a los mappers ya conocidos.
 */
@Component
public class PersonaMapper {

    public Persona toEntity(PersonaRequestDTO dto) {
        Persona entidad = new Persona();
        entidad.setNombre(dto.getNombre());
        entidad.setPrimerApellido(dto.getPrimerApellido());
        entidad.setSegundoApellido(dto.getSegundoApellido());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setCorreo(dto.getCorreo());
        return entidad;
    }

    public void updateEntity(Persona entidad, PersonaRequestDTO dto) {
        entidad.setNombre(dto.getNombre());
        entidad.setPrimerApellido(dto.getPrimerApellido());
        entidad.setSegundoApellido(dto.getSegundoApellido());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setCorreo(dto.getCorreo());
    }

    public PersonaResponseDTO toResponseDTO(Persona entidad) {
        return PersonaResponseDTO.builder()
                .idPersona(entidad.getIdPersona())
                .nombre(entidad.getNombre())
                .primerApellido(entidad.getPrimerApellido())
                .segundoApellido(entidad.getSegundoApellido())
                .fechaNacimiento(entidad.getFechaNacimiento())
                .correo(entidad.getCorreo())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .cuentaConfirmada(entidad.getCuentaConfirmada())
                .build();
    }
}
