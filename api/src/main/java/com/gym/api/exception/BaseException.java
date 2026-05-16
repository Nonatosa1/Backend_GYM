package com.gym.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Clase base de todas las excepciones personalizadas del sistema.
 *
 * Cualquier excepcion del dominio del negocio debe extender esta clase.
 * Hereda de RuntimeException porque las excepciones no chequeadas son
 * mas apropiadas para errores de negocio: no obligan a declararlas en
 * las firmas de los metodos y se propagan naturalmente hasta el
 * manejador global.
 *
 * Cada subclase predefine el codigo HTTP y la clave de error que le
 * corresponde, de modo que cuando un servicio lanza una excepcion no
 * necesita preocuparse de la respuesta HTTP final: la jerarquia ya lo
 * tiene resuelto. El manejador global lee estos atributos y construye
 * la respuesta apropiada automaticamente.
 *
 * Ejemplo de uso desde un servicio:
 *   throw new ResourceNotFoundException("Usuario", id);
 *
 * El cliente de la API recibira una respuesta HTTP 404 con un cuerpo
 * JSON que indica claramente que el usuario no fue encontrado.
 */
@Getter
public class BaseException extends RuntimeException {

    /**
     * Codigo HTTP que debe acompañar la respuesta de error.
     * Lo define cada subclase segun el tipo de error que representa.
     */
    private final HttpStatus status;

    /**
     * Clave programatica que identifica el tipo de error.
     * Permite al cliente detectar el tipo de error sin tener que parsear
     * el mensaje, lo cual es mucho mas robusto.
     */
    private final String errorKey;

    /**
     * Constructor que recibe todos los datos necesarios para describir el
     * error. El mensaje se pasa al constructor padre (RuntimeException)
     * para que sea accesible mediante getMessage().
     */
    public BaseException(HttpStatus status, String errorKey, String message) {
        super(message);
        this.status = status;
        this.errorKey = errorKey;
    }
}
