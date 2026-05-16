package com.gym.api.util;

import com.gym.api.dto.request.FeedbackRequestDTO;
import com.gym.api.dto.response.FeedbackResponseDTO;
import com.gym.api.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    private final UsuarioMapper usuarioMapper;
    private final ClaseMapper claseMapper;

    public Feedback toEntity(FeedbackRequestDTO dto) {
        Feedback entidad = new Feedback();
        entidad.setComentario(dto.getComentario());
        return entidad;
    }

    public void updateEntity(Feedback entidad, FeedbackRequestDTO dto) {
        entidad.setComentario(dto.getComentario());
    }

    public FeedbackResponseDTO toResponseDTO(Feedback entidad) {
        return FeedbackResponseDTO.builder()
                .idFeedback(entidad.getIdFeedback())
                .usuario(entidad.getUsuario() != null ? usuarioMapper.toResponseDTO(entidad.getUsuario()) : null)
                .clase(entidad.getClase() != null ? claseMapper.toResponseDTO(entidad.getClase()) : null)
                .comentario(entidad.getComentario())
                .habilitado(entidad.getHabilitado())
                .fechaAlta(entidad.getFechaAlta())
                .fechaBaja(entidad.getFechaBaja())
                .build();
    }
}
