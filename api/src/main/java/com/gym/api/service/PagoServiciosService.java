package com.gym.api.service;

import com.gym.api.dto.request.PagoServiciosRequestDTO;
import com.gym.api.dto.response.PagoServiciosResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con pagos de servicios.
 *
 * La implementacion subyacente resuelve las relaciones con RentaServicios
 * y MetodoPago, y aplica las validaciones de monto monetario declaradas
 * en el DTO de peticion.
 */
public interface PagoServiciosService {

    PagoServiciosResponseDTO crear(PagoServiciosRequestDTO request);

    PagoServiciosResponseDTO consultarPorId(Integer id);

    List<PagoServiciosResponseDTO> listarTodos();

    List<PagoServiciosResponseDTO> listarHabilitados();

    PagoServiciosResponseDTO actualizar(Integer id, PagoServiciosRequestDTO request);

    void eliminar(Integer id);
}
