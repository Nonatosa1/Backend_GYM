package com.gym.api.controller;

import com.gym.api.dto.request.LoginRequestDTO;
import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;
import com.gym.api.service.LoginService;
import com.gym.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST que expone los endpoints HTTP para gestion de usuarios.
 *
 * Aunque la estructura es similar a la de otros controladores del proyecto,
 * la riqueza del modulo se evidencia en los posibles codigos de respuesta
 * que pueden devolverse en las operaciones de creacion y actualizacion:
 *
 *   HTTP 201 Created cuando el usuario se crea con exito
 *   HTTP 200 OK cuando una consulta o actualizacion es exitosa
 *   HTTP 204 No Content cuando la eliminacion logica es exitosa
 *   HTTP 400 Bad Request cuando las validaciones del DTO fallan
 *   HTTP 404 Not Found cuando el usuario, la persona o el rol no existen
 *   HTTP 409 Conflict cuando el nombre de cuenta ya esta en uso
 *
 * Todas estas respuestas se manejan automaticamente gracias al
 * GlobalExceptionHandler diseñado al inicio del proyecto. El controlador
 * se mantiene delgado y enfocado en orquestar el flujo HTTP.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO creado = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un usuario por su identificador")
    public ResponseEntity<UsuarioResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios, incluidos los dados de baja")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los usuarios activos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(usuarioService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un usuario")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
