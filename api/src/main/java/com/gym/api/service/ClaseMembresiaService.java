package com.gym.api.service;

import com.gym.api.dto.request.ClaseMembresiaRequestDTO;
import com.gym.api.dto.response.ClaseMembresiaResponseDTO;

import java.util.List;

public interface ClaseMembresiaService {

    ClaseMembresiaResponseDTO crear(ClaseMembresiaRequestDTO request);

    ClaseMembresiaResponseDTO consultarPorId(Integer id);

    List<ClaseMembresiaResponseDTO> listarTodos();

    List<ClaseMembresiaResponseDTO> listarHabilitados();

    ClaseMembresiaResponseDTO actualizar(Integer id, ClaseMembresiaRequestDTO request);

    void eliminar(Integer id);
}
