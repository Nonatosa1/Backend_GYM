package com.gym.api.controller;

import com.gym.api.dto.request.InventarioUsuarioRequestDTO;
import com.gym.api.dto.response.InventarioUsuarioResponseDTO;
import com.gym.api.service.InventarioUsuarioService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de
 * asignaciones de inventario a usuarios.
 */
@RestController
@RequestMapping("/api/inventario-usuarios")
@RequiredArgsConstructor
@Tag(name = "Asignaciones de Inventario", description = "Gestion de asignaciones de equipo de inventario a usuarios")
public class InventarioUsuarioController {

    private final InventarioUsuarioService inventarioUsuarioService;

    @PostMapping
    @Operation(summary = "Crear una nueva asignacion de inventario")
    public ResponseEntity<InventarioUsuarioResponseDTO> crear(@Valid @RequestBody InventarioUsuarioRequestDTO request) {
        InventarioUsuarioResponseDTO creada = inventarioUsuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una asignacion por su identificador")
    public ResponseEntity<InventarioUsuarioResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioUsuarioService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las asignaciones, incluidas las dadas de baja")
    public ResponseEntity<List<InventarioUsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(inventarioUsuarioService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las asignaciones activas")
    public ResponseEntity<List<InventarioUsuarioResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(inventarioUsuarioService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una asignacion existente")
    public ResponseEntity<InventarioUsuarioResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody InventarioUsuarioRequestDTO request) {
        return ResponseEntity.ok(inventarioUsuarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente una asignacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioUsuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
