package com.gym.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepcion que se lanza cuando una operacion entra en conflicto con el
 * estado actual del sistema.
 *
 * Esta excepcion es especifica para situaciones donde la peticion es
 * valida en si misma pero choca con datos que ya existen. La diferencia
 * con BusinessException es sutil pero importante: BusinessException trata
 * sobre violaciones de reglas semanticas del dominio, mientras que
 * ConflictException trata sobre conflictos con el estado actual de los
 * datos almacenados. Ejemplos tipicos:
 *
 * - Intentar crear un usuario con un nombre de cuenta que ya existe.
 * - Intentar registrar un correo electronico ya en uso.
 * - Intentar crear un catalogo con un valor duplicado.
 *
 * Devuelve HTTP 409 (Conflict), que es el codigo estandar para este
 * tipo de situaciones. El mensaje debe indicar claramente que dato
 * provoca el conflicto para que el cliente pueda corregirlo.
 *
 * En la practica, ConflictException y BusinessException pueden parecer
 * intercambiables en algunos casos. La regla general es: si el problema
 * surge porque ya existe algo similar, es Conflict. Si el problema surge
 * porque la operacion en si no esta permitida por alguna regla, es
 * Business.
 */
public class ConflictException extends BaseException {

    private static final String ERROR_KEY = "CONFLICTO";

    public ConflictException(String mensaje) {
        super(HttpStatus.CONFLICT, ERROR_KEY, mensaje);
    }
}
