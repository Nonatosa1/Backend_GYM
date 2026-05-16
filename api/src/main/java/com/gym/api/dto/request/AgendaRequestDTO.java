package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar una entrada de agenda.
 *
 * Una entrada de agenda es la reserva que hace un usuario sobre un
 * espacio fisico durante un periodo de tiempo. Combina tres relaciones
 * simultaneas: el usuario que reserva, el area donde se realizara la
 * actividad, y el espacio fisico concreto que se reserva. Cada una se
 * representa en este DTO por su identificador correspondiente.
 *
 * Notese que tanto idArea como idEspacioFisico estan presentes, aunque
 * conceptualmente el espacio fisico ya pertenece a un area. Esta
 * redundancia es una decision de diseño del diagrama original que
 * facilita consultas, pero impone al servicio la responsabilidad de
 * validar la coherencia: el area declarada debe coincidir con el area
 * a la que pertenece el espacio fisico reservado.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una entrada de agenda")
public class AgendaRequestDTO {

    @NotBlank(message = "La descripcion de la agenda es obligatoria")
    @Size(min = 3, max = 300, message = "La descripcion debe tener entre 3 y 300 caracteres")
    @Schema(description = "Descripcion libre de la reserva", example = "Sesion de entrenamiento personal", maxLength = 300)
    private String agenda;

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario que realiza la reserva", example = "1")
    private Integer idUsuario;

    @NotNull(message = "El identificador del area es obligatorio")
    @Schema(description = "Identificador del area donde se realizara la actividad", example = "1")
    private Integer idArea;

    @NotNull(message = "El identificador del espacio fisico es obligatorio")
    @Schema(description = "Identificador del espacio fisico concreto que se reserva", example = "1")
    private Short idEspacioFisico;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Momento de inicio de la reserva", example = "2026-05-12T18:00:00")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Schema(description = "Momento de fin de la reserva", example = "2026-05-12T19:00:00")
    private LocalDateTime fechaFin;
}
