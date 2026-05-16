package com.gym.api.service;

import com.gym.api.dto.request.AgendaRequestDTO;
import com.gym.api.dto.response.AgendaResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con entradas de agenda.
 *
 * La implementacion subyacente coordina la resolucion de tres relaciones
 * simultaneas (usuario, area, espacio fisico), aplica dos validaciones
 * semanticas (consistencia de fechas y coherencia entre area y espacio
 * fisico), y maneja la auditoria como el resto de los servicios. Esa
 * complejidad esta encapsulada en la clase de implementacion para que
 * los consumidores de este contrato no necesiten conocerla.
 */
public interface AgendaService {

    AgendaResponseDTO crear(AgendaRequestDTO request);

    AgendaResponseDTO consultarPorId(Integer id);

    List<AgendaResponseDTO> listarTodos();

    List<AgendaResponseDTO> listarHabilitados();

    AgendaResponseDTO actualizar(Integer id, AgendaRequestDTO request);

    void eliminar(Integer id);
}
