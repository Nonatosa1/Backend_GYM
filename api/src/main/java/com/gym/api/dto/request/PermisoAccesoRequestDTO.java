package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar un permiso de acceso.
 *
 * Un permiso de acceso es el derecho que se otorga a un usuario para
 * acceder a un area especifica durante un periodo de tiempo. La
 * granularidad horaria de las fechas (LocalDateTime, no LocalDate) permite
 * modelar restricciones detalladas como "acceso solo en horario de tarde"
 * o "acceso unicamente durante eventos especiales".
 *
 * Notese que este DTO no incluye una validacion automatica de que
 * fechaFin sea posterior a fechaInicio. Esa validacion semantica se hace
 * en el servicio porque las anotaciones estandar de Bean Validation no
 * tienen una forma directa de validar relaciones entre campos. Cuando
 * lleguemos al servicio veremos como se implementa esa validacion con
 * BusinessException.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un permiso de acceso")
public class PermisoAccesoRequestDTO {

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario al que se otorga el permiso", example = "1")
    private Integer idUsuario;

    @NotNull(message = "El identificador del area es obligatorio")
    @Schema(description = "Identificador del area sobre la que se otorga el permiso", example = "1")
    private Integer idArea;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Momento en que comienza la vigencia del permiso", example = "2026-05-12T08:00:00")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Schema(description = "Momento en que finaliza la vigencia del permiso", example = "2026-05-12T20:00:00")
    private LocalDateTime fechaFin;
}
