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
 * DTO que representa los datos de una persona devueltos por la API.
 *
 * Incluye todos los campos de la entidad excepto los puramente internos.
 * La fecha de nacimiento se devuelve como LocalDate (sin hora), que
 * Jackson serializa por defecto en formato ISO 8601 (YYYY-MM-DD).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de una persona devueltos por la API")
public class PersonaResponseDTO {

    @Schema(description = "Identificador unico de la persona", example = "1")
    private Integer idPersona;

    @Schema(description = "Nombre de la persona", example = "Juan Carlos")
    private String nombre;

    @Schema(description = "Primer apellido", example = "Garcia")
    private String primerApellido;

    @Schema(description = "Segundo apellido", example = "Lopez")
    private String segundoApellido;

    @Schema(description = "Fecha de nacimiento", example = "1990-05-15")
    private LocalDate fechaNacimiento;

    @Schema(description = "Correo electronico de contacto", example = "juan.garcia@correo.com")
    private String correo;

    @Schema(description = "Indica si el registro esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;

    @Schema(description = "Indicador de cuando una persona ya valido su cuenta")
    private Boolean cuentaConfirmada;
}
