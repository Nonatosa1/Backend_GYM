package com.gym.api.service;

import com.gym.api.dto.request.EspacioFisicoRequestDTO;
import com.gym.api.dto.response.EspacioFisicoResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con espacios fisicos.
 *
 * Como el resto de las interfaces de servicio del proyecto, declara las
 * operaciones CRUD basicas. La complejidad real de implementar estas
 * operaciones (resolver la relacion con Area, aplicar auditoria) queda
 * encapsulada en la clase de implementacion.
 */
public interface EspacioFisicoService {

    EspacioFisicoResponseDTO crear(EspacioFisicoRequestDTO request);

    EspacioFisicoResponseDTO consultarPorId(Short id);

    List<EspacioFisicoResponseDTO> listarTodos();

    List<EspacioFisicoResponseDTO> listarHabilitados();

    EspacioFisicoResponseDTO actualizar(Short id, EspacioFisicoRequestDTO request);

    void eliminar(Short id);
}
