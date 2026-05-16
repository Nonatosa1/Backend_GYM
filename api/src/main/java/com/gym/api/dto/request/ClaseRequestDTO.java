package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * DTO para crear o actualizar una clase del gimnasio.
 *
 * Una clase es la plantilla que define que actividad se imparte, quien
 * la imparte, en que horario, con que cupo maximo y bajo que formato.
 * La programacion semanal de los dias en que se imparte se modela aparte
 * en el modulo de ClaseDia, y las asistencias individuales se modelan en
 * el modulo de Asistencia.
 *
 * Notese que la relacion con el instructor apunta a Persona (idPersonaResponsable)
 * y no a Usuario. Esta decision permite designar como responsable a alguien
 * que no necesariamente tiene cuenta en el sistema, util para instructores
 * externos o invitados que no requieren credenciales propias.
 *
 * Los campos horaInicio y horaTermino son de tipo LocalTime porque
 * representan solo la hora del dia, sin asociarla a una fecha especifica.
 * Esto refleja el caracter recurrente del horario: la clase ocurre a la
 * misma hora cada vez que se imparte, sin importar el dia.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una clase del gimnasio")
public class ClaseRequestDTO {

    @NotNull(message = "El identificador del instructor responsable es obligatorio")
    @Schema(description = "Identificador de la persona que imparte la clase", example = "1")
    private Integer idPersonaResponsable;

    @NotNull(message = "El numero maximo de alumnos es obligatorio")
    @Min(value = 1, message = "El cupo maximo debe ser de al menos un alumno")
    @Schema(description = "Numero maximo de alumnos que pueden inscribirse", example = "20")
    private Byte noMaxAlumnos;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Schema(description = "Hora del dia en que comienza la clase", example = "18:30:00")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de termino es obligatoria")
    @Schema(description = "Hora del dia en que termina la clase", example = "19:30:00")
    private LocalTime horaTermino;

    @NotNull(message = "El identificador del tipo de clase es obligatorio")
    @Schema(description = "Identificador del tipo de clase (grupal, individual, etc.)", example = "1")
    private Byte idTipoClase;

    @NotNull(message = "El identificador de la actividad es obligatorio")
    @Schema(description = "Identificador de la actividad fisica practicada", example = "1")
    private Byte idActividad;
}
