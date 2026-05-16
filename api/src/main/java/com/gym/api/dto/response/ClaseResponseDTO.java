package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO que representa los datos de una clase devueltos por la API.
 *
 * Esta es una respuesta rica con tres entidades anidadas: el instructor
 * responsable (Persona), el tipo de clase y la actividad. Esto permite
 * que el cliente reciba en una sola peticion toda la informacion para
 * mostrar la clase completa en una interfaz, incluyendo nombre del
 * instructor, formato de la clase y disciplina practicada.
 *
 * Las horas se serializan en formato ISO 8601 (HH:mm:ss). Por ejemplo,
 * "18:30:00" representa las dieciocho horas con treinta minutos, sin
 * informacion de fecha asociada.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una clase devueltos por la API")
public class ClaseResponseDTO {

    @Schema(description = "Identificador unico de la clase", example = "1")
    private Integer idClase;

    @Schema(description = "Persona responsable de impartir la clase")
    private PersonaResponseDTO personaResponsable;

    @Schema(description = "Numero maximo de alumnos por sesion", example = "20")
    private Byte noMaxAlumnos;

    @Schema(description = "Hora del dia en que comienza la clase", example = "18:30:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora del dia en que termina la clase", example = "19:30:00")
    private LocalTime horaTermino;

    @Schema(description = "Tipo o formato de la clase")
    private TipoClaseResponseDTO tipoClase;

    @Schema(description = "Actividad fisica practicada en la clase")
    private ActividadResponseDTO actividad;

    @Schema(description = "Indica si la clase esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
