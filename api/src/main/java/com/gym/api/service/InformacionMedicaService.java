package com.gym.api.service;

import com.gym.api.dto.request.InformacionMedicaRequestDTO;
import com.gym.api.dto.response.InformacionMedicaResponseDTO;

import java.util.List;

/**
 * Contrato de operaciones de negocio relacionadas con informacion medica
 * de los usuarios.
 *
 * Esta interfaz es estructuralmente igual a las demas del proyecto, pero
 * vale la pena recordar que la implementacion subyacente maneja datos
 * sensibles que en una version mas madura del sistema requeririan
 * controles de acceso especificos.
 */
public interface InformacionMedicaService {

    InformacionMedicaResponseDTO crear(InformacionMedicaRequestDTO request);

    InformacionMedicaResponseDTO consultarPorId(Integer id);

    List<InformacionMedicaResponseDTO> listarTodos();

    List<InformacionMedicaResponseDTO> listarHabilitados();

    InformacionMedicaResponseDTO actualizar(Integer id, InformacionMedicaRequestDTO request);

    void eliminar(Integer id);
}
