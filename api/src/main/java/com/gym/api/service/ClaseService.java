package com.gym.api.service;

import com.gym.api.dto.request.ClaseRequestDTO;
import com.gym.api.dto.response.ClaseResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con clases del gimnasio.
 *
 * La implementacion subyacente coordina la resolucion de tres relaciones
 * (persona responsable, tipo de clase, actividad) y la validacion
 * semantica de horarios. La complejidad esta encapsulada en la
 * implementacion para que los consumidores de este contrato no necesiten
 * conocerla.
 */
public interface ClaseService {

    ClaseResponseDTO crear(ClaseRequestDTO request);

    ClaseResponseDTO consultarPorId(Integer id);

    List<ClaseResponseDTO> listarTodos();

    List<ClaseResponseDTO> listarHabilitados();

    ClaseResponseDTO actualizar(Integer id, ClaseRequestDTO request);

    void eliminar(Integer id);
}
