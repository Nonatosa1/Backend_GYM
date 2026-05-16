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
 * DTO para crear o actualizar un elemento del inventario.
 *
 * Un elemento de inventario representa un equipo concreto del gimnasio.
 * Cada elemento pertenece a un area especifica (donde se encuentra
 * fisicamente) y a una categoria o tipo de inventario (que clasifica el
 * equipo). Por eso este DTO incluye dos referencias por ID que el
 * servicio se encargara de resolver.
 *
 * El nombre del campo principal "inventario" coincide deliberadamente con
 * el nombre de la entidad, siguiendo el modelado del diagrama original.
 * Conceptualmente, ese campo es el nombre o identificador descriptivo
 * del equipo, como "Caminadora 1" o "Mancuernas 5kg".
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un elemento de inventario")
public class InventarioRequestDTO {

    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre del equipo debe tener entre 2 y 150 caracteres")
    @Schema(description = "Nombre o identificador descriptivo del equipo", example = "Caminadora 1", maxLength = 150)
    private String inventario;

    @NotNull(message = "El identificador del area es obligatorio")
    @Schema(description = "Identificador del area donde se ubica el equipo", example = "1")
    private Integer idArea;

    @NotNull(message = "El identificador del tipo de inventario es obligatorio")
    @Schema(description = "Identificador del tipo o categoria del equipo", example = "1")
    private Byte idTipoInventario;
}
