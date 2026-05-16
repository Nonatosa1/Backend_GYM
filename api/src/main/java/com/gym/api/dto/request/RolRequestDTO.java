package com.gym.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa los datos que el cliente envia para crear o actualizar
 * un rol.
 *
 * Notese que solo contiene el campo "rol" (el nombre del rol). No incluye
 * el ID porque la base de datos lo genera automaticamente al crear, y
 * para actualizar el ID viaja como parametro en la URL. Tampoco incluye
 * habilitado ni fechas de auditoria porque esos campos los gestiona el
 * sistema internamente: el cliente no deberia poder manipular esa
 * informacion directamente.
 *
 * Las anotaciones de validacion (@NotBlank, @Size) son del paquete
 * jakarta.validation y se activan cuando el controlador recibe el DTO
 * con la anotacion @Valid. Si los datos no cumplen las restricciones,
 * Spring lanza automaticamente una MethodArgumentNotValidException que
 * nuestro GlobalExceptionHandler intercepta y convierte en una respuesta
 * HTTP 400 con la lista detallada de errores.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un rol")
public class RolRequestDTO {

    /**
     * Nombre del rol. Es obligatorio (no puede ser null ni cadena vacia
     * ni solo espacios en blanco gracias a @NotBlank) y debe respetar el
     * limite de 25 caracteres definido en la entidad y en la base de datos.
     */
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 25, message = "El nombre del rol no puede exceder los 25 caracteres")
    @Schema(description = "Nombre del rol", example = "Administrador", maxLength = 25)
    private String rol;
}
