package com.gym.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura JSON que viaja al cliente cuando ocurre un error en la API.
 *
 * Este DTO define el contrato uniforme de errores. Cualquier respuesta de
 * error de la API, sin importar su tipo o capa de origen, tendra esta
 * estructura. Esto permite que los clientes (frontend, apps moviles,
 * otros servicios) implementen un manejo de errores uniforme sin
 * necesidad de tratar cada tipo de error como un caso especial.
 *
 * La anotacion @JsonInclude(NON_NULL) hace que los campos que valgan null
 * sean omitidos del JSON final, lo cual mantiene las respuestas limpias.
 * Por ejemplo, si no hay errores detallados, el campo "errores" no
 * aparecera en la respuesta, en lugar de mostrar "errores": null.
 *
 * La anotacion @Schema de SpringDoc OpenAPI documenta esta estructura en
 * Swagger UI, de modo que los consumidores de la API saben exactamente
 * que esperar cuando reciben un error.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Estructura estandar de respuesta de error de la API")
public class ErrorResponse {

    /**
     * Momento exacto en que se genero el error. Util para correlacionar
     * con los logs del servidor cuando hace falta investigar un problema.
     */
    @Schema(description = "Momento en que se genero el error", example = "2026-05-12T15:30:45")
    private LocalDateTime timestamp;

    /**
     * Codigo HTTP que acompaña esta respuesta. Aunque el cliente puede
     * obtenerlo de la respuesta HTTP en si, incluirlo en el cuerpo
     * facilita el logging y el manejo desde el lado del consumidor.
     */
    @Schema(description = "Codigo HTTP de la respuesta", example = "404")
    private Integer status;

    /**
     * Clave programatica que identifica el tipo de error. A diferencia del
     * mensaje (que es para humanos), esta clave esta pensada para que el
     * cliente pueda detectar programaticamente el tipo de error y actuar
     * en consecuencia. Por ejemplo, RECURSO_NO_ENCONTRADO, VALIDACION,
     * CONFLICTO, REGLA_NEGOCIO.
     */
    @Schema(description = "Clave que identifica el tipo de error", example = "RECURSO_NO_ENCONTRADO")
    private String error;

    /**
     * Mensaje descriptivo del error en lenguaje natural. Esta pensado para
     * mostrarse al usuario final o para depuracion. Debe ser claro pero no
     * revelar detalles internos del sistema (nombres de tablas, trazas, etc.).
     */
    @Schema(description = "Mensaje descriptivo del error", example = "El usuario con ID 42 no existe")
    private String message;

    /**
     * Ruta del endpoint donde ocurrio el error. Util para diagnostico
     * cuando se reportan problemas o se revisan logs.
     */
    @Schema(description = "Ruta del endpoint donde ocurrio el error", example = "/api/usuarios/42")
    private String path;

    /**
     * Lista opcional de errores detallados. Se usa principalmente para
     * errores de validacion donde multiples campos pueden fallar
     * simultaneamente. Por ejemplo, si un usuario envia un formulario con
     * el correo invalido y la contraseña corta, este campo contendra
     * ambos errores detallados.
     *
     * Cuando no hay errores detallados, este campo se omite del JSON
     * gracias a la anotacion @JsonInclude(NON_NULL) de la clase.
     */
    @Schema(description = "Lista detallada de errores de validacion por campo")
    private List<String> errores;
}
