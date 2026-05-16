package com.gym.api.service;

import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con usuarios del sistema.
 *
 * Las operaciones aqui declaradas son las CRUD estandar, pero su
 * implementacion subyacente tiene complejidad adicional que no se ve en
 * esta interfaz: el manejo seguro de contraseñas con BCrypt, la validacion
 * de unicidad del nombre de cuenta, y la resolucion de las relaciones con
 * Persona y Rol a partir de sus identificadores. Esa complejidad esta
 * deliberadamente encapsulada en la implementacion para que los consumidores
 * de este contrato no necesiten conocerla.
 *
 * Si en el futuro se añade autenticacion al sistema, esta interfaz probablemente
 * crecera con metodos como autenticar(usuario, password), cambiarPassword, etc.
 * Por ahora se mantiene minima con las operaciones CRUD basicas.
 */
public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    UsuarioResponseDTO consultarPorId(Integer id);

    List<UsuarioResponseDTO> listarTodos();

    List<UsuarioResponseDTO> listarHabilitados();

    UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO request);

    void eliminar(Integer id);
}
