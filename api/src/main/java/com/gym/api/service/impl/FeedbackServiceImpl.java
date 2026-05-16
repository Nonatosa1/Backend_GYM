package com.gym.api.service.impl;

import com.gym.api.dto.request.FeedbackRequestDTO;
import com.gym.api.dto.response.FeedbackResponseDTO;
import com.gym.api.entity.Clase;
import com.gym.api.entity.Feedback;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.FeedbackMapper;
import com.gym.api.repository.ClaseRepository;
import com.gym.api.repository.FeedbackRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClaseRepository claseRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackResponseDTO crear(FeedbackRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        Feedback entidad = feedbackMapper.toEntity(request);
        entidad.setUsuario(usuario);
        entidad.setClase(clase);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Feedback guardado = feedbackRepository.save(entidad);
        return feedbackMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackResponseDTO consultarPorId(Integer id) {
        Feedback entidad = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));
        return feedbackMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponseDTO> listarTodos() {
        return feedbackRepository.findAll()
                .stream()
                .map(feedbackMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponseDTO> listarHabilitados() {
        return feedbackRepository.findAll()
                .stream()
                .filter(Feedback::getHabilitado)
                .map(feedbackMapper::toResponseDTO)
                .toList();
    }

    @Override
    public FeedbackResponseDTO actualizar(Integer id, FeedbackRequestDTO request) {
        Feedback entidad = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getIdUsuario()));

        Clase clase = claseRepository.findById(request.getIdClase())
                .orElseThrow(() -> new ResourceNotFoundException("Clase", request.getIdClase()));

        feedbackMapper.updateEntity(entidad, request);
        entidad.setUsuario(usuario);
        entidad.setClase(clase);

        Feedback actualizado = feedbackRepository.save(entidad);
        return feedbackMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        Feedback entidad = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        feedbackRepository.save(entidad);
    }
}
