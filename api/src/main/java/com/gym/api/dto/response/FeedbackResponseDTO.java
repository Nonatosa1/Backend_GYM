package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un feedback devueltos por la API")
public class FeedbackResponseDTO {

    @Schema(description = "Identificador unico del feedback", example = "1")
    private Integer idFeedback;

    @Schema(description = "Usuario que dio el feedback")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Clase sobre la que se da el feedback")
    private ClaseResponseDTO clase;

    @Schema(description = "Comentario del usuario", example = "Excelente sesion, el instructor explico muy bien")
    private String comentario;

    @Schema(description = "Indica si el feedback esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
