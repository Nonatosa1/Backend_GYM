package com.gym.api.service;

import com.gym.api.dto.request.AsistenciaRequestDTO;
import com.gym.api.dto.response.AsistenciaResponseDTO;

import java.util.List;

public interface AsistenciaService {

    AsistenciaResponseDTO crear(AsistenciaRequestDTO request);

    AsistenciaResponseDTO consultarPorId(Integer id);

    List<AsistenciaResponseDTO> listarTodos();

    List<AsistenciaResponseDTO> listarHabilitados();

    AsistenciaResponseDTO actualizar(Integer id, AsistenciaRequestDTO request);

    void eliminar(Integer id);
}
