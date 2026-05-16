package com.gym.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO que representa los datos que la API devuelve cuando se consulta un rol.
 *
 * A diferencia del DTO de peticion, este incluye todos los campos que el
 * cliente puede necesitar visualizar: el ID, el nombre, el estado de
 * habilitacion y las fechas de auditoria. Mantener separados los DTOs de
 * peticion y respuesta nos da flexibilidad para evolucionar cada uno
 * independientemente sin romper la API hacia atras.
 *
 * La anotacion @JsonInclude(NON_NULL) omite los campos nulos del JSON
 * final. Esto es util principalmente para fechaBaja, que solo tiene
 * valor cuando el rol ha sido dado de baja logicamente. Si el rol esta
 * activo, ese campo simplemente no aparecera en la respuesta en lugar
 * de mostrarse como null.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un rol devueltos por la API")
public class RolResponseDTO {

    @Schema(description = "Identificador unico del rol", example = "1")
    private Byte idRol;

    @Schema(description = "Nombre del rol", example = "Administrador")
    private String rol;

    @Schema(description = "Indica si el rol esta activo en el sistema", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica el registro (null si esta activo)")
    private LocalDateTime fechaBaja;
}
