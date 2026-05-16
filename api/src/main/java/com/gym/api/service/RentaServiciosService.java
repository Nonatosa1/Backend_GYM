package com.gym.api.service;

import com.gym.api.dto.request.RentaServiciosRequestDTO;
import com.gym.api.dto.response.RentaServiciosResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con rentas de servicios.
 *
 * La implementacion subyacente resuelve las relaciones con Usuario e
 * Inventario, valida la consistencia de las fechas (vencimiento posterior
 * a inicio), y gestiona la auditoria automatica como el resto de los
 * servicios del proyecto.
 */
public interface RentaServiciosService {

    RentaServiciosResponseDTO crear(RentaServiciosRequestDTO request);

    RentaServiciosResponseDTO consultarPorId(Integer id);

    List<RentaServiciosResponseDTO> listarTodos();

    List<RentaServiciosResponseDTO> listarHabilitados();

    RentaServiciosResponseDTO actualizar(Integer id, RentaServiciosRequestDTO request);

    void eliminar(Integer id);
}
