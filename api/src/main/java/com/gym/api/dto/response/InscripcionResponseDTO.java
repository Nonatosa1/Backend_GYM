package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa los datos de una inscripcion devueltos por la API.
 *
 * Incluye informacion anidada tanto de la membresia adquirida como del
 * usuario inscrito. Esto permite al cliente ver en una sola peticion el
 * contexto completo de la inscripcion, incluyendo nombre de la
 * membresia, precio, duracion en dias, datos del usuario, y demas.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una inscripcion devueltos por la API")
public class InscripcionResponseDTO {

    @Schema(description = "Identificador unico de la inscripcion", example = "1")
    private Integer idInscripcion;

    @Schema(description = "Membresia adquirida")
    private MembresiaResponseDTO membresia;

    @Schema(description = "Usuario inscrito")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Fecha en que comienza la vigencia", example = "2026-05-12")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha en que finaliza la vigencia", example = "2026-06-12")
    private LocalDate fechaVencimiento;

    @Schema(description = "Indica si la inscripcion esta activa", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
