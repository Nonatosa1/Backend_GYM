package com.gym.api.service;

import com.gym.api.dto.request.InventarioUsuarioRequestDTO;
import com.gym.api.dto.response.InventarioUsuarioResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con asignaciones de
 * inventario a usuarios.
 *
 * La implementacion subyacente resuelve las relaciones con Usuario e
 * Inventario, aplica la validacion semantica de consistencia de fechas
 * que ya conocemos de PermisoAcceso, y gestiona la auditoria automatica.
 */
public interface InventarioUsuarioService {

    InventarioUsuarioResponseDTO crear(InventarioUsuarioRequestDTO request);

    InventarioUsuarioResponseDTO consultarPorId(Integer id);

    List<InventarioUsuarioResponseDTO> listarTodos();

    List<InventarioUsuarioResponseDTO> listarHabilitados();

    InventarioUsuarioResponseDTO actualizar(Integer id, InventarioUsuarioRequestDTO request);

    void eliminar(Integer id);
}
