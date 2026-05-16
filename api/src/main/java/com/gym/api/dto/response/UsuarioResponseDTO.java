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
 * DTO que representa los datos de un usuario devueltos por la API.
 *
 * Una decision de seguridad fundamental: este DTO no incluye el campo
 * password en absoluto. Bajo ninguna circunstancia el servidor debe devolver
 * la contraseña, ni siquiera su hash. Aunque el hash de BCrypt es
 * tecnicamente irreversible y exponerlo no permitiria a un atacante
 * recuperar la contraseña original directamente, es una mala practica
 * innecesaria que abre posibilidades de ataque (por ejemplo, comparacion
 * con bases de datos de contraseñas filtradas en otras brechas). La regla
 * es simple: el cliente envia la contraseña, el servidor nunca la devuelve.
 *
 * En cuanto a las relaciones, en lugar de devolver solo los IDs de persona
 * y rol (lo cual obligaria al cliente a hacer peticiones adicionales para
 * mostrar el nombre), anidamos los DTOs de respuesta completos de Persona
 * y Rol. Esto hace que el cliente reciba en una sola peticion toda la
 * informacion que probablemente necesite para mostrar al usuario.
 *
 * Esta decision tiene un costo: la respuesta es mas grande y requiere
 * cargar las relaciones desde la base de datos. Para nuestro caso de uso
 * es aceptable porque ambas relaciones son referencias simples a entidades
 * pequeñas. Si en el futuro las consultas se vuelven costosas, se podria
 * crear un DTO alternativo "ligero" que solo incluya IDs.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Datos de un usuario devueltos por la API")
public class UsuarioResponseDTO {

    @Schema(description = "Identificador unico del usuario", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nombre de cuenta para iniciar sesion", example = "juan_perez")
    private String usuario;

    @Schema(description = "Datos de la persona a la que pertenece esta cuenta")
    private PersonaResponseDTO persona;

    @Schema(description = "Rol que define los permisos del usuario")
    private RolResponseDTO rol;

    @Schema(description = "Indica si el usuario esta activo", example = "true")
    private Boolean habilitado;

    @Schema(description = "Momento en que se creo el registro")
    private LocalDateTime fechaAlta;

    @Schema(description = "Momento en que se dio de baja logica (null si esta activo)")
    private LocalDateTime fechaBaja;
}
