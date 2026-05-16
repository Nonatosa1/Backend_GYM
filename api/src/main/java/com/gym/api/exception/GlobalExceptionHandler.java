package com.gym.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Manejador global de excepciones para toda la API.
 *
 * Esta clase intercepta cualquier excepcion lanzada desde cualquier
 * controlador, la transforma en una respuesta HTTP coherente con la
 * estructura definida en ErrorResponse, y la devuelve al cliente. Gracias
 * a esto, los controladores y servicios pueden lanzar excepciones
 * libremente sin preocuparse por construir respuestas HTTP: ese trabajo
 * se centraliza aqui.
 *
 * La anotacion @RestControllerAdvice combina dos comportamientos:
 *   - @ControllerAdvice: hace que los metodos de esta clase apliquen a
 *     todos los controladores de la aplicacion
 *   - Modifica las respuestas para serializarlas como JSON, igual que un
 *     @RestController
 *
 * Cada metodo anotado con @ExceptionHandler maneja un tipo especifico de
 * excepcion. Cuando una excepcion se lanza en cualquier capa de la
 * aplicacion, Spring busca el manejador mas especifico disponible y
 * delega la construccion de la respuesta a el.
 *
 * El orden de los manejadores en el codigo no importa: Spring usa el tipo
 * de excepcion para elegir el manejador correcto.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja todas las excepciones que heredan de BaseException, que son
     * las excepciones personalizadas del dominio del sistema.
     *
     * Como BaseException ya tiene el codigo HTTP y la clave de error
     * embebidos, este metodo solo necesita extraerlos y construir la
     * respuesta uniforme. Esto hace que añadir nuevas subclases de
     * BaseException no requiera tocar este manejador en absoluto: el
     * codigo nuevo se integra automaticamente.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> manejarBaseException(BaseException ex, HttpServletRequest request) {
        log.warn("Excepcion de negocio: {} - {}", ex.getErrorKey(), ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatus().value())
                .error(ex.getErrorKey())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    /**
     * Maneja errores de validacion de datos de entrada, tipicamente
     * lanzados cuando un DTO recibido en una peticion no pasa las
     * anotaciones de validacion como @NotNull, @Size, @Email, etc.
     *
     * Esta excepcion es especial porque puede contener multiples errores
     * a la vez (varios campos invalidos en el mismo formulario). Por eso
     * la respuesta incluye una lista detallada de errores ademas del
     * mensaje principal.
     *
     * Devuelve HTTP 400 (Bad Request) porque la peticion en si esta mal
     * formada desde el punto de vista de los datos enviados.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> erroresDetallados = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        log.warn("Errores de validacion: {}", erroresDetallados);

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("VALIDACION")
                .message("Los datos enviados no son validos")
                .path(request.getRequestURI())
                .errores(erroresDetallados)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja cualquier excepcion que no haya sido capturada por los
     * manejadores anteriores. Es el "ultimo recurso" del sistema y
     * captura errores inesperados como NullPointerException, errores de
     * conexion a base de datos, fallos de configuracion, etc.
     *
     * Devuelve HTTP 500 (Internal Server Error) y un mensaje generico
     * al cliente para no revelar detalles internos del sistema que
     * podrian ser explotados por un atacante. Sin embargo, el detalle
     * completo del error se registra en los logs del servidor para
     * permitir diagnostico interno.
     *
     * Este manejador es la red de seguridad que garantiza que la API
     * nunca devuelve respuestas con trazas de pila ni informacion
     * sensible, sin importar que tan mal salga algo internamente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGenerica(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado en la API", ex);

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("ERROR_INTERNO")
                .message("Ocurrio un error inesperado. Si el problema persiste, contacte al equipo de soporte.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
