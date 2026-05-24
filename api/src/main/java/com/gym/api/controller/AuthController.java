package com.gym.api.controller;

import com.gym.api.dto.request.LoginRequestDTO;
import com.gym.api.dto.response.ConfirmacionResponseDTO;
import com.gym.api.dto.response.LoginResponseDTO;
import com.gym.api.service.PersonaService;
import com.gym.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticacion.
 *
 * Expone el endpoint de login que valida las credenciales del usuario y
 * devuelve sus datos basicos junto con el rol en texto.
 *
 * Posibles respuestas:
 *   200 OK: credenciales validas, devuelve LoginResponseDTO
 *   400 Bad Request: datos del request invalidos (campos vacios)
 *   401 Unauthorized: usuario o contraseña incorrectos, o cuenta deshabilitada
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Endpoints de autenticacion de usuarios")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PersonaService personaService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion en el sistema")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @GetMapping("/confirmar/{token}")
    @Operation(summary = "Confirmar la cuenta usando el token recibido por correo")
    public ResponseEntity<ConfirmacionResponseDTO> confirmarCuenta(@PathVariable String token) {
        return ResponseEntity.ok(personaService.confirmarCuenta(token));
    }
}
