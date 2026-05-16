package com.gym.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepcion que se lanza cuando se viola una regla del negocio.
 *
 * A diferencia de ResourceNotFoundException, que indica un problema
 * tecnico de existencia, esta excepcion indica que la operacion solicitada
 * no puede completarse porque viola alguna regla del dominio del negocio.
 * Ejemplos tipicos en este sistema serian:
 *
 * - Intentar inscribir a un usuario a una clase que ya alcanzo su cupo
 *   maximo.
 * - Intentar inscribirse a una membresia cuando ya existe una vigente.
 * - Intentar registrar un pago cuyo monto supera el total pendiente.
 * - Intentar agendar un horario que se solapa con otra reserva.
 *
 * Devuelve HTTP 422 (Unprocessable Entity), que es el codigo apropiado
 * para errores semanticos: la peticion esta bien formada (no es un 400),
 * pero su contenido viola reglas del dominio.
 *
 * El mensaje debe ser claro y, si es posible, indicar al usuario que
 * accion alternativa puede tomar para resolver la situacion.
 */
public class BusinessException extends BaseException {

    private static final String ERROR_KEY = "REGLA_NEGOCIO";

    public BusinessException(String mensaje) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, ERROR_KEY, mensaje);
    }
}
