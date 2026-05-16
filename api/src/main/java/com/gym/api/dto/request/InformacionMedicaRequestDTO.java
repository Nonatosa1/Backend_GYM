package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para crear o actualizar la informacion medica de un usuario.
 *
 * Este es el modulo mas sensible del proyecto porque maneja datos de
 * salud, considerados informacion personal sensible bajo la mayoria de
 * las regulaciones de proteccion de datos.
 *
 * Los campos obligatorios son aquellos que son criticos para casos de
 * emergencia (tipo de sangre, telefono de contacto). Los campos
 * opcionales son los que pueden no aplicar a todos los usuarios
 * (alergias, condiciones de salud).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar o actualizar la informacion medica de un usuario")
public class InformacionMedicaRequestDTO {

    @NotNull(message = "El identificador del usuario es obligatorio")
    @Schema(description = "Identificador del usuario al que pertenece la informacion medica", example = "1")
    private Integer idUsuario;

    @NotBlank(message = "El tipo de sangre es obligatorio")
    @Size(max = 5, message = "El tipo de sangre no puede exceder los 5 caracteres")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "El tipo de sangre debe seguir el formato estandar (ejemplo: A+, O-, AB+)")
    @Schema(description = "Tipo de sangre del usuario en notacion estandar", example = "O+", maxLength = 5)
    private String tipoSangre;

    @Size(max = 500, message = "El campo de alergias no puede exceder los 500 caracteres")
    @Schema(description = "Alergias conocidas del usuario", example = "Polen, mariscos", maxLength = 500)
    private String alergias;

    @Size(max = 500, message = "El campo de condiciones de salud no puede exceder los 500 caracteres")
    @Schema(description = "Condiciones de salud relevantes para el entrenamiento", example = "Hipertension controlada", maxLength = 500)
    private String condicionesDeSalud;

    @NotBlank(message = "El telefono de emergencia es obligatorio")
    @Size(max = 15, message = "El telefono no puede exceder los 15 caracteres")
    @Schema(description = "Telefono de contacto en caso de emergencia", example = "5512345678", maxLength = 15)
    private String telefonoEmergencia;
}
