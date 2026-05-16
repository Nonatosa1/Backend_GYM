package com.gym.api.service;

import com.gym.api.dto.request.MembresiaRequestDTO;
import com.gym.api.dto.response.MembresiaResponseDTO;

import java.util.List;

public interface MembresiaService {

    MembresiaResponseDTO crear(MembresiaRequestDTO request);

    MembresiaResponseDTO consultarPorId(Short id);

    List<MembresiaResponseDTO> listarTodos();

    List<MembresiaResponseDTO> listarHabilitados();

    MembresiaResponseDTO actualizar(Short id, MembresiaRequestDTO request);

    void eliminar(Short id);
}
