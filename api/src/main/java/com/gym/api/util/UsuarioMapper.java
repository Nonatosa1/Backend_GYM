package com.gym.api.util;

import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;
import com.gym.api.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de las conversiones entre la entidad Usuario y sus DTOs.
 *
 * Este mapper introduce un patron interesante respecto a los anteriores:
 * delega en otros mappers cuando necesita convertir las entidades
 * relacionadas. Concretamente, para construir el UsuarioResponseDTO
 * necesitamos convertir la Persona y el Rol asociados, y para eso usamos
 * los mappers correspondientes. Esto es un ejemplo concreto del principio
 * de composicion: cada componente hace su trabajo bien y los componentes
 * complejos se apoyan en los simples.
 *
 * Otra particularidad importante: el metodo toEntity NO establece las
 * relaciones Persona ni Rol. Esto es deliberado. El DTO trae solo los IDs
 * (idPersona, idRol), no objetos completos, y el mapper no tiene forma de
 * resolverlos sin hablar con la base de datos. Esa resolucion es
 * responsabilidad del servicio, que despues de invocar toEntity se encarga
 * de buscar las entidades referenciadas y asignarlas al usuario. El
 * password tampoco se copia aqui porque requiere ser hasheado primero, lo
 * cual tambien es responsabilidad del servicio.
 *
 * La inyeccion por constructor (gracias a @RequiredArgsConstructor) trae
 * los dos mappers como dependencias finales, garantizando que esten
 * disponibles cuando se necesiten.
 */
@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final PersonaMapper personaMapper;
    private final RolMapper rolMapper;

    /**
     * Convierte un DTO de peticion en una entidad Usuario. Solo copia el
     * campo "usuario" del DTO. El password debe ser establecido por el
     * servicio despues de hashearlo. Las relaciones persona y rol deben ser
     * resueltas por el servicio a partir de sus IDs y establecidas con los
     * setters correspondientes.
     */
    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario entidad = new Usuario();
        entidad.setUsuario(dto.getUsuario());
        return entidad;
    }

    /**
     * Actualiza una entidad Usuario existente con los datos del DTO. Igual
     * que en toEntity, las relaciones y el password no se gestionan aqui;
     * son responsabilidad del servicio.
     */
    public void updateEntity(Usuario entidad, UsuarioRequestDTO dto) {
        entidad.setUsuario(dto.getUsuario());
    }

    /**
     * Convierte una entidad Usuario en un DTO de respuesta. Delega en los
     * mappers de Persona y Rol para convertir las entidades relacionadas.
     * Notese que el password jamas se incluye en la respuesta por razones
     * de seguridad.
     */
    public UsuarioResponseDTO toResponseDTO(Usuario entidad) {
        return UsuarioResponseDTO.builder()
                .idUsuario(entidad.getIdUsuario())
                .usuario(entidad.getUsuario())
                .persona(entidad.getPersona() != null ? personaMapper.toResponseDTO(entidad.getPersona()) : null)
                .rol(entidad.getRol() != null ? rolMapper.toResponseDTO(entidad.getRol()) : null)
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
