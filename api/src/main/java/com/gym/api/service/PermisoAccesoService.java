package com.gym.api.service;

import com.gym.api.dto.request.PermisoAccesoRequestDTO;
import com.gym.api.dto.response.PermisoAccesoResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con permisos de acceso.
 *
 * La implementacion subyacente tiene complejidad adicional que no se ve
 * en esta interfaz: resolucion de las relaciones con Usuario y Area, y
 * validacion semantica de que la fecha de fin sea posterior a la de
 * inicio. Esa complejidad esta encapsulada en la clase de implementacion
 * para que los consumidores de este contrato no necesiten conocerla.
 */
public interface PermisoAccesoService {

    PermisoAccesoResponseDTO crear(PermisoAccesoRequestDTO request);

    PermisoAccesoResponseDTO consultarPorId(Integer id);

    List<PermisoAccesoResponseDTO> listarTodos();

    List<PermisoAccesoResponseDTO> listarHabilitados();

    PermisoAccesoResponseDTO actualizar(Integer id, PermisoAccesoRequestDTO request);

    void eliminar(Integer id);
}
