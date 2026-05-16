package com.gym.api.controller;

import com.gym.api.dto.request.TipoClaseRequestDTO;
import com.gym.api.dto.response.TipoClaseResponseDTO;
import com.gym.api.service.TipoClaseService;
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
@RequestMapping("/api/tipos-clase")
@RequiredArgsConstructor
@Tag(name = "Tipos de Clase", description = "Gestion del catalogo de tipos de clase")
public class TipoClaseController {

    private final TipoClaseService tipoClaseService;

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de clase")
    public ResponseEntity<TipoClaseResponseDTO> crear(@Valid @RequestBody TipoClaseRequestDTO request) {
        TipoClaseResponseDTO creado = tipoClaseService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un tipo de clase por su identificador")
    public ResponseEntity<TipoClaseResponseDTO> consultarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(tipoClaseService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de clase, incluidos los dados de baja")
    public ResponseEntity<List<TipoClaseResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tipoClaseService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los tipos de clase activos")
    public ResponseEntity<List<TipoClaseResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(tipoClaseService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de clase existente")
    public ResponseEntity<TipoClaseResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody TipoClaseRequestDTO request) {
        return ResponseEntity.ok(tipoClaseService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un tipo de clase")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        tipoClaseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
