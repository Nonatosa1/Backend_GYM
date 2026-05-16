package com.gym.api.service;

import com.gym.api.dto.request.InscripcionRequestDTO;
import com.gym.api.dto.response.InscripcionResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con inscripciones de
 * membresia.
 *
 * La implementacion subyacente resuelve las relaciones con Membresia y
 * Usuario, valida la coherencia de fechas (vencimiento posterior al
 * inicio), y gestiona la auditoria automatica como el resto de los
 * servicios del proyecto.
 */
public interface InscripcionService {

    InscripcionResponseDTO crear(InscripcionRequestDTO request);

    InscripcionResponseDTO consultarPorId(Integer id);

    List<InscripcionResponseDTO> listarTodos();

    List<InscripcionResponseDTO> listarHabilitados();

    InscripcionResponseDTO actualizar(Integer id, InscripcionRequestDTO request);

    void eliminar(Integer id);
}
