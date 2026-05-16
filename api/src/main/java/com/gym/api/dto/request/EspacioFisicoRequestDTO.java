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

/**
 * DTO para crear o actualizar un espacio fisico del gimnasio.
 *
 * Un espacio fisico es una ubicacion concreta dentro de un area, como una
 * caminadora especifica, un casillero, una sala de yoga, o el ring de
 * boxeo. Cada espacio fisico pertenece a exactamente un area, lo cual se
 * representa en este DTO mediante el campo idArea, siguiendo el patron
 * de referencia por identificador que ya aplicamos en el modulo de
 * Usuario.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un espacio fisico")
public class EspacioFisicoRequestDTO {

    @NotBlank(message = "El nombre del espacio fisico es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    @Schema(description = "Nombre o identificador descriptivo del espacio fisico", example = "Caminadora 3", maxLength = 150)
    private String espacioFisico;

    @NotNull(message = "El identificador del area es obligatorio")
    @Schema(description = "Identificador del area a la que pertenece el espacio fisico", example = "1")
    private Integer idArea;
}
