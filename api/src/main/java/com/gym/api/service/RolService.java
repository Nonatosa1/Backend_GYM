package com.gym.api.service;

import com.gym.api.dto.request.RolRequestDTO;
import com.gym.api.dto.response.RolResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con roles.
 *
 * Esta interfaz define que operaciones puede realizar el sistema sobre
 * los roles, sin comprometerse a una implementacion concreta. Los
 * controladores van a depender de esta interfaz, no de la clase concreta
 * que la implementa, lo cual respeta el principio de inversion de
 * dependencias.
 *
 * Si en el futuro hace falta cambiar la implementacion (por ejemplo, para
 * añadir cache, o para usar una fuente de datos distinta), bastara con
 * crear una nueva clase que implemente esta interfaz, sin modificar los
 * controladores ni el resto del sistema.
 */
public interface RolService {

    /**
     * Crea un nuevo rol en el sistema.
     * El rol se crea habilitado por defecto y con fecha de alta automatica.
     */
    RolResponseDTO crear(RolRequestDTO request);

    /**
     * Consulta un rol especifico por su identificador.
     * Si el rol no existe, lanza ResourceNotFoundException.
     */
    RolResponseDTO consultarPorId(Byte id);

    /**
     * Devuelve todos los roles del sistema, incluidos los dados de baja.
     * Para obtener solo los activos se debe usar el metodo listarHabilitados.
     */
    List<RolResponseDTO> listarTodos();

    /**
     * Devuelve solo los roles que estan activos (no dados de baja).
     */
    List<RolResponseDTO> listarHabilitados();

    /**
     * Actualiza los datos de un rol existente.
     * Si el rol no existe, lanza ResourceNotFoundException.
     */
    RolResponseDTO actualizar(Byte id, RolRequestDTO request);

    /**
     * Elimina logicamente un rol (lo marca como deshabilitado y registra
     * la fecha de baja). No realiza borrado fisico para preservar la
     * integridad referencial con las tablas que referencian este rol.
     * Si el rol no existe, lanza ResourceNotFoundException.
     */
    void eliminar(Byte id);
}
