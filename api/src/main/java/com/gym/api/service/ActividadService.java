package com.gym.api.service;

import com.gym.api.dto.request.ActividadRequestDTO;
import com.gym.api.dto.response.ActividadResponseDTO;

import java.util.List;

public interface ActividadService {

    ActividadResponseDTO crear(ActividadRequestDTO request);

    ActividadResponseDTO consultarPorId(Byte id);

    List<ActividadResponseDTO> listarTodos();

    List<ActividadResponseDTO> listarHabilitados();

    ActividadResponseDTO actualizar(Byte id, ActividadRequestDTO request);

    void eliminar(Byte id);
}
