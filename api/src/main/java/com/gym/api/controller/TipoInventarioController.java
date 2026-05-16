package com.gym.api.controller;

import com.gym.api.dto.request.TipoInventarioRequestDTO;
import com.gym.api.dto.response.TipoInventarioResponseDTO;
import com.gym.api.service.TipoInventarioService;
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

@RestController
@RequestMapping("/api/tipos-inventario")
@RequiredArgsConstructor
@Tag(name = "Tipos de Inventario", description = "Gestion del catalogo de tipos de inventario")
public class TipoInventarioController {

    private final TipoInventarioService tipoInventarioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de inventario")
    public ResponseEntity<TipoInventarioResponseDTO> crear(@Valid @RequestBody TipoInventarioRequestDTO request) {
        TipoInventarioResponseDTO creado = tipoInventarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un tipo de inventario por su identificador")
    public ResponseEntity<TipoInventarioResponseDTO> consultarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(tipoInventarioService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de inventario, incluidos los dados de baja")
    public ResponseEntity<List<TipoInventarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tipoInventarioService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los tipos de inventario activos")
    public ResponseEntity<List<TipoInventarioResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(tipoInventarioService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de inventario existente")
    public ResponseEntity<TipoInventarioResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody TipoInventarioRequestDTO request) {
        return ResponseEntity.ok(tipoInventarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un tipo de inventario")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        tipoInventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
