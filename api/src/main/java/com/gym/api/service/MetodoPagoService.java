package com.gym.api.service;

import com.gym.api.dto.request.MetodoPagoRequestDTO;
import com.gym.api.dto.response.MetodoPagoResponseDTO;

import java.util.List;

public interface MetodoPagoService {

    MetodoPagoResponseDTO crear(MetodoPagoRequestDTO request);

    MetodoPagoResponseDTO consultarPorId(Byte id);

    List<MetodoPagoResponseDTO> listarTodos();

    List<MetodoPagoResponseDTO> listarHabilitados();

    MetodoPagoResponseDTO actualizar(Byte id, MetodoPagoRequestDTO request);

    void eliminar(Byte id);
}
