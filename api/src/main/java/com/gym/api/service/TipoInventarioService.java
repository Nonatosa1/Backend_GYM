package com.gym.api.service;

import com.gym.api.dto.request.TipoInventarioRequestDTO;
import com.gym.api.dto.response.TipoInventarioResponseDTO;

import java.util.List;

public interface TipoInventarioService {

    TipoInventarioResponseDTO crear(TipoInventarioRequestDTO request);

    TipoInventarioResponseDTO consultarPorId(Byte id);

    List<TipoInventarioResponseDTO> listarTodos();

    List<TipoInventarioResponseDTO> listarHabilitados();

    TipoInventarioResponseDTO actualizar(Byte id, TipoInventarioRequestDTO request);

    void eliminar(Byte id);
}
