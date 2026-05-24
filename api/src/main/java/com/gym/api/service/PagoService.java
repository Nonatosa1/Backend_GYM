package com.gym.api.service;

import com.gym.api.dto.DashboardPagosDTO;
import com.gym.api.dto.request.PagoRequestDTO;
import com.gym.api.dto.response.PagoResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con pagos de membresia.
 *
 * La implementacion subyacente resuelve la relacion con Inscripcion y
 * aplica las validaciones de monto monetario declaradas en el DTO.
 */
public interface PagoService {

    PagoResponseDTO crear(PagoRequestDTO request);

    PagoResponseDTO consultarPorId(Integer id);

    List<PagoResponseDTO> listarTodos();

    List<PagoResponseDTO> listarHabilitados();

    PagoResponseDTO actualizar(Integer id, PagoRequestDTO request);

    void eliminar(Integer id);

    DashboardPagosDTO obtenerDashboardPagos(Integer idUsuario);
}
