package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO para crear o actualizar una persona en el sistema.
 *
 * Esta es la primera entidad rica del dominio del negocio que modelamos,
 * con campos como nombre, apellidos, fecha de nacimiento y correo
 * electronico. Cada campo tiene validaciones especificas que reflejan
 * las reglas semanticas del dominio:
 *
 *   nombre y primer apellido son obligatorios porque toda persona debe
 *   identificarse al menos con esos datos
 *
 *   segundo apellido es opcional para acomodar personas sin segundo
 *   apellido o de contextos internacionales donde no aplica
 *
 *   fecha de nacimiento usa @Past para garantizar que sea anterior al
 *   momento actual: nadie nace en el futuro
 *
 *   correo usa @Email para validar formato estandar de direcciones de
 *   correo electronico
 *
 * Notese que las anotaciones se evaluan en orden cuando el cliente envia
 * datos. Si por ejemplo el correo viene null, @NotBlank fallara antes de
 * que @Email tenga oportunidad de evaluar, evitando errores en cadena.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar una persona")
public class PersonaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre de la persona", example = "Juan Carlos", maxLength = 100)
    private String nombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El primer apellido debe tener entre 2 y 100 caracteres")
    @Schema(description = "Primer apellido de la persona", example = "Garcia", maxLength = 100)
    private String primerApellido;

    @Size(max = 100, message = "El segundo apellido no puede exceder los 100 caracteres")
    @Schema(description = "Segundo apellido opcional", example = "Lopez", maxLength = 100)
    private String segundoApellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior al dia de hoy")
    @Schema(description = "Fecha de nacimiento (formato ISO 8601)", example = "1990-05-15")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo electronico es obligatorio")
    @Email(message = "El correo electronico no tiene un formato valido")
    @Size(max = 150, message = "El correo electronico no puede exceder los 150 caracteres")
    @Schema(description = "Correo electronico de contacto", example = "juan.garcia@correo.com", maxLength = 150)
    private String correo;
}
