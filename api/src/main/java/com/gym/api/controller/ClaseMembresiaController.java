package com.gym.api.controller;

import com.gym.api.dto.request.ClaseMembresiaRequestDTO;
import com.gym.api.dto.response.ClaseMembresiaResponseDTO;
import com.gym.api.service.ClaseMembresiaService;
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
@RequestMapping("/api/clase-membresias")
@RequiredArgsConstructor
@Tag(name = "Clase-Membresias", description = "Asociacion de clases a tipos de membresia")
public class ClaseMembresiaController {

    private final ClaseMembresiaService claseMembresiaService;

    @PostMapping
    @Operation(summary = "Asociar una clase con una membresia")
    public ResponseEntity<ClaseMembresiaResponseDTO> crear(@Valid @RequestBody ClaseMembresiaRequestDTO request) {
        ClaseMembresiaResponseDTO creada = claseMembresiaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una asociacion por su identificador")
    public ResponseEntity<ClaseMembresiaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(claseMembresiaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las asociaciones, incluidas las dadas de baja")
    public ResponseEntity<List<ClaseMembresiaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(claseMembresiaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las asociaciones activas")
    public ResponseEntity<List<ClaseMembresiaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(claseMembresiaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una asociacion existente")
    public ResponseEntity<ClaseMembresiaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClaseMembresiaRequestDTO request) {
        return ResponseEntity.ok(claseMembresiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una asociacion")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        claseMembresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
