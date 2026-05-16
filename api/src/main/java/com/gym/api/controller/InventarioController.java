package com.gym.api.controller;

import com.gym.api.dto.request.InventarioRequestDTO;
import com.gym.api.dto.response.InventarioResponseDTO;
import com.gym.api.service.InventarioService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de elementos
 * de inventario.
 */
@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
@Tag(name = "Inventarios", description = "Gestion del inventario del gimnasio")
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo elemento de inventario")
    public ResponseEntity<InventarioResponseDTO> crear(@Valid @RequestBody InventarioRequestDTO request) {
        InventarioResponseDTO creado = inventarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un elemento de inventario por su identificador")
    public ResponseEntity<InventarioResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los elementos de inventario, incluidos los dados de baja")
    public ResponseEntity<List<InventarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los elementos de inventario activos")
    public ResponseEntity<List<InventarioResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(inventarioService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un elemento de inventario existente")
    public ResponseEntity<InventarioResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody InventarioRequestDTO request) {
        return ResponseEntity.ok(inventarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un elemento de inventario")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
