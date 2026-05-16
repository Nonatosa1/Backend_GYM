package com.gym.api.service;

import com.gym.api.dto.request.ClaseDiaRequestDTO;
import com.gym.api.dto.response.ClaseDiaResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con la programacion
 * semanal de clases.
 *
 * La implementacion resuelve las relaciones con Clase y Dia, validando
 * que ambas entidades existan antes de crear la asociacion.
 */
public interface ClaseDiaService {

    ClaseDiaResponseDTO crear(ClaseDiaRequestDTO request);

    ClaseDiaResponseDTO consultarPorId(Integer id);

    List<ClaseDiaResponseDTO> listarTodos();

    List<ClaseDiaResponseDTO> listarHabilitados();

    ClaseDiaResponseDTO actualizar(Integer id, ClaseDiaRequestDTO request);

    void eliminar(Integer id);
}
