package com.gym.api.service;

import com.gym.api.dto.request.InventarioRequestDTO;
import com.gym.api.dto.response.InventarioResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con elementos de inventario.
 *
 * La implementacion subyacente coordina la resolucion de las relaciones
 * con Area y TipoInventario, y aplica la auditoria automatica estandar.
 */
public interface InventarioService {

    InventarioResponseDTO crear(InventarioRequestDTO request);

    InventarioResponseDTO consultarPorId(Integer id);

    List<InventarioResponseDTO> listarTodos();

    List<InventarioResponseDTO> listarHabilitados();

    InventarioResponseDTO actualizar(Integer id, InventarioRequestDTO request);

    void eliminar(Integer id);
}
