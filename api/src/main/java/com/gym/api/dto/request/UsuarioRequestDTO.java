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
 * DTO para crear o actualizar un usuario del sistema.
 *
 * Este DTO introduce varias novedades importantes respecto a los modulos
 * anteriores. La primera es la forma de representar las relaciones con
 * otras entidades: en lugar de anidar objetos completos, recibimos solo
 * los identificadores idPersona e idRol. Esta decision tiene varias
 * ventajas que conviene tener presentes:
 *
 *   La semantica es clara: el cliente esta diciendo "asocia este nuevo
 *   usuario con la persona X y el rol Y que ya existen".
 *
 *   Evita ambiguedades sobre que hacer si las entidades relacionadas ya
 *   existen, son distintas a las almacenadas, o tienen datos parciales.
 *
 *   Mantiene el DTO plano y sencillo, sin anidamientos complicados.
 *
 *   Respeta el orden natural de creacion: primero existe la persona,
 *   despues se le asocia un usuario.
 *
 * La segunda novedad es la validacion de longitud minima para la
 * contraseña. Una contraseña de menos de 8 caracteres se considera
 * inaceptablemente debil en estandares modernos de seguridad. El maximo
 * de 200 caracteres es generoso pero coincide con la longitud de la
 * columna en la base de datos, aunque vale la pena destacar que esa
 * longitud almacenara el hash de BCrypt, no la contraseña en texto plano.
 *
 * IMPORTANTE: el password que llega en este DTO esta en texto plano. El
 * servicio se encarga de hashearlo con BCrypt ANTES de pasarlo a la
 * entidad para su persistencia. Bajo ninguna circunstancia se almacena
 * la contraseña sin hashear.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear o actualizar un usuario")
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre de cuenta para iniciar sesion", example = "juan_perez", maxLength = 50)
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 200, message = "La contraseña debe tener entre 8 y 200 caracteres")
    @Schema(description = "Contraseña en texto plano. El sistema la hashea antes de almacenarla.",
            example = "Mi_Contraseña_Segura_123",
            minLength = 8)
    private String password;

    @NotNull(message = "El identificador de la persona es obligatorio")
    @Schema(description = "Identificador de la persona a la que pertenece esta cuenta", example = "1")
    private Integer idPersona;

    @NotNull(message = "El identificador del rol es obligatorio")
    @Schema(description = "Identificador del rol que define los permisos del usuario", example = "1")
    private Byte idRol;
}
