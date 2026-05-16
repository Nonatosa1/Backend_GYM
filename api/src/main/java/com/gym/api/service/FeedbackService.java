package com.gym.api.service;

import com.gym.api.dto.request.FeedbackRequestDTO;
import com.gym.api.dto.response.FeedbackResponseDTO;

import java.util.List;

public interface FeedbackService {

    FeedbackResponseDTO crear(FeedbackRequestDTO request);

    FeedbackResponseDTO consultarPorId(Integer id);

    List<FeedbackResponseDTO> listarTodos();

    List<FeedbackResponseDTO> listarHabilitados();

    FeedbackResponseDTO actualizar(Integer id, FeedbackRequestDTO request);

    void eliminar(Integer id);
}
