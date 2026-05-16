package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO para crear o actualizar un registro de asistencia.
 *
 * Cada asistencia representa una sesion individual de una clase a la que
 * un usuario debia acudir, indicando si efectivamente asistio o no. La
 * separacion entre "se registro" y "asistio" es importante para analisis
 * de comportamiento: permite detectar usuarios que se inscriben pero no
 * van, lo cual es informacion util para la operacion del gimnasio.
 *
 * La fecha de la asistencia usa @PastOrPresent porque no tiene sentido
 * registrar una asistencia futura: el evento ya debe haber ocurrido
 * (presente) o haber ocurrido antes (pasado). Notese que aqui usamos
 * LocalDate (solo dia, sin hora) porque la hora exacta esta implicita
 * en el horario fijo de la clase a la que pertenece la asistencia.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar una asistencia a una clase")
public class AsistenciaRequestDTO {

    @NotNull(message = "El identificador de la clase es obligatorio")
    @Schema(description = "Identificador de la clase a la que corresponde la asistencia", example = "1")
    private Integer idClase;

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario cuya asistencia se registra", example = "1")
    private Integer idUsuario;

    @NotNull(message = "La fecha de la asistencia es obligatoria")
    @PastOrPresent(message = "La fecha de la asistencia no puede ser futura")
    @Schema(description = "Fecha de la sesion a la que corresponde la asistencia", example = "2026-05-12")
    private LocalDate fecha;

    @NotNull(message = "Debe indicar si el usuario asistio o no")
    @Schema(description = "Indica si el usuario efectivamente asistio a la sesion", example = "true")
    private Boolean asistio;
}
