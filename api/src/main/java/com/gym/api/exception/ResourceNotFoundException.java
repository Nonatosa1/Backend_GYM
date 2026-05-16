package com.gym.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepcion que se lanza cuando se intenta acceder a un recurso que no
 * existe en la base de datos.
 *
 * Esta es probablemente la excepcion mas usada del sistema. Cualquier
 * busqueda por ID que no encuentre resultados debe lanzarla. El cliente
 * recibira una respuesta HTTP 404 (Not Found) con un mensaje claro
 * indicando que recurso no fue encontrado.
 *
 * Ofrece dos constructores para distintos niveles de detalle:
 *
 * Cuando se conoce el tipo de recurso y su identificador:
 *   throw new ResourceNotFoundException("Usuario", id);
 *   Mensaje generado: "Usuario con identificador {id} no encontrado"
 *
 * Cuando se necesita un mensaje completamente personalizado:
 *   throw new ResourceNotFoundException("No se encontro inscripcion vigente para el usuario indicado");
 *
 * El primer constructor es preferible para errores estandar porque
 * genera mensajes consistentes en toda la API. El segundo se reserva
 * para casos donde la situacion requiere mas contexto.
 */
public class ResourceNotFoundException extends BaseException {

    private static final String ERROR_KEY = "RECURSO_NO_ENCONTRADO";

    /**
     * Constructor para casos donde se conoce el tipo de recurso y su id.
     * Genera un mensaje estandarizado del tipo:
     * "Usuario con identificador 42 no encontrado".
     */
    public ResourceNotFoundException(String recurso, Object identificador) {
        super(
                HttpStatus.NOT_FOUND,
                ERROR_KEY,
                String.format("%s con identificador %s no encontrado", recurso, identificador)
        );
    }

    /**
     * Constructor para casos donde se necesita un mensaje personalizado
     * mas alla del formato estandar.
     */
    public ResourceNotFoundException(String mensaje) {
        super(HttpStatus.NOT_FOUND, ERROR_KEY, mensaje);
    }
}
