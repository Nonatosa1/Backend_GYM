package com.gym.api.service;

import com.gym.api.dto.request.TipoClaseRequestDTO;
import com.gym.api.dto.response.TipoClaseResponseDTO;

import java.util.List;

public interface TipoClaseService {

    TipoClaseResponseDTO crear(TipoClaseRequestDTO request);

    TipoClaseResponseDTO consultarPorId(Byte id);

    List<TipoClaseResponseDTO> listarTodos();

    List<TipoClaseResponseDTO> listarHabilitados();

    TipoClaseResponseDTO actualizar(Byte id, TipoClaseRequestDTO request);

    void eliminar(Byte id);
}
