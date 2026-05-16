package com.gym.api.service;

import com.gym.api.dto.request.AreaRequestDTO;
import com.gym.api.dto.response.AreaResponseDTO;

import java.util.List;

public interface AreaService {

    AreaResponseDTO crear(AreaRequestDTO request);

    AreaResponseDTO consultarPorId(Integer id);

    List<AreaResponseDTO> listarTodos();

    List<AreaResponseDTO> listarHabilitados();

    AreaResponseDTO actualizar(Integer id, AreaRequestDTO request);

    void eliminar(Integer id);
}
