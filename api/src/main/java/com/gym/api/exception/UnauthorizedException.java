package com.gym.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepcion lanzada cuando las credenciales de autenticacion son invalidas.
 *
 * Se devuelve HTTP 401 Unauthorized, que es el codigo estandar para
 * fallos de autenticacion. El mensaje deliberadamente NO distingue entre
 * "usuario no existe" y "contraseña incorrecta" para no dar pistas a
 * potenciales atacantes sobre que usuarios existen en el sistema.
 */
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, "CREDENCIALES_INVALIDAS", message);
    }
}
