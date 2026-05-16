package com.gym.api.service;

import com.gym.api.dto.request.DetallePagoRequestDTO;
import com.gym.api.dto.response.DetallePagoResponseDTO;

import java.util.List;

public interface DetallePagoService {

    DetallePagoResponseDTO crear(DetallePagoRequestDTO request);

    DetallePagoResponseDTO consultarPorId(Integer id);

    List<DetallePagoResponseDTO> listarTodos();

    List<DetallePagoResponseDTO> listarHabilitados();

    DetallePagoResponseDTO actualizar(Integer id, DetallePagoRequestDTO request);

    void eliminar(Integer id);
}
