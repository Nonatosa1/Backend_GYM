package com.gym.api.service;

import com.gym.api.dto.request.DiaRequestDTO;
import com.gym.api.dto.response.DiaResponseDTO;

import java.util.List;

public interface DiaService {

    DiaResponseDTO crear(DiaRequestDTO request);

    DiaResponseDTO consultarPorId(Byte id);

    List<DiaResponseDTO> listarTodos();

    List<DiaResponseDTO> listarHabilitados();

    DiaResponseDTO actualizar(Byte id, DiaRequestDTO request);

    void eliminar(Byte id);
}
