package com.gym.api.util;

import com.gym.api.dto.request.RolRequestDTO;
import com.gym.api.dto.response.RolResponseDTO;
import com.gym.api.entity.Rol;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria responsable de convertir entre la entidad Rol y sus DTOs.
 *
 * Separar las conversiones en su propia clase mantiene el servicio
 * concentrado en la logica de negocio. El servicio "decide que hacer", el
 * mapper "transforma datos". Esta separacion respeta el principio de
 * responsabilidad unica y hace el codigo mas facil de probar y mantener.
 *
 * La anotacion @Component registra esta clase como un bean de Spring,
 * para que pueda inyectarse en los servicios que la necesiten.
 *
 * En proyectos mas grandes, se suele usar una libreria como MapStruct para
 * generar los mappers automaticamente a partir de las firmas de los
 * metodos. Para este proyecto, dado que tenemos veintiseis entidades pero
 * estructuras relativamente simples, escribir los mappers manualmente es
 * suficiente y mantiene el codigo mas explicito.
 */
@Component
public class RolMapper {

    /**
     * Convierte un DTO de peticion en una entidad. Se usa al crear un
     * rol nuevo. No copia el ID porque la base lo genera automaticamente,
     * y no copia campos de auditoria porque esos los rellena el servicio.
     */
    public Rol toEntity(RolRequestDTO dto) {
        Rol entidad = new Rol();
        entidad.setRol(dto.getRol());
        return entidad;
    }

    /**
     * Actualiza una entidad existente con los datos de un DTO de peticion.
     * Se usa al actualizar un rol. Solo modifica los campos que el cliente
     * puede cambiar, dejando intactos los gestionados por el sistema.
     */
    public void updateEntity(Rol entidad, RolRequestDTO dto) {
        entidad.setRol(dto.getRol());
    }

    /**
     * Convierte una entidad en un DTO de respuesta. Se usa para devolver
     * informacion al cliente. Copia todos los campos relevantes.
     */
    public RolResponseDTO toResponseDTO(Rol entidad) {
        return RolResponseDTO.builder()
                .idRol(entidad.getIdRol())
                .rol(entidad.getRol())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
