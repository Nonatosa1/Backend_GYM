package com.gym.api.service;

import com.gym.api.dto.request.PersonaRequestDTO;
import com.gym.api.dto.request.RegistroRequestDTO;
import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.ConfirmacionResponseDTO;
import com.gym.api.dto.response.PersonaResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con personas.
 *
 * Define las operaciones CRUD basicas para gestionar registros de personas
 * en el sistema. Persona es la entidad base del modelo de identidad, sobre
 * la cual se construye Usuario.
 */
public interface PersonaService {

    PersonaResponseDTO crear(RegistroRequestDTO Persona);

    PersonaResponseDTO consultarPorId(Integer id);

    List<PersonaResponseDTO> listarTodos();

    List<PersonaResponseDTO> listarHabilitados();

    PersonaResponseDTO actualizar(Integer id, PersonaRequestDTO request);

    void eliminar(Integer id);

    List<PersonaResponseDTO> buscarPorNombre(String termino);

    ConfirmacionResponseDTO confirmarCuenta(String token);
}
