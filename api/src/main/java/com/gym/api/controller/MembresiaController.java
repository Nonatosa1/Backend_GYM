package com.gym.api.controller;

import com.gym.api.dto.request.MembresiaRequestDTO;
import com.gym.api.dto.response.MembresiaResponseDTO;
import com.gym.api.service.MembresiaService;
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
@RequestMapping("/api/membresias")
@RequiredArgsConstructor
@Tag(name = "Membresias", description = "Gestion del catalogo de membresias del gimnasio")
public class MembresiaController {

    private final MembresiaService membresiaService;

    @PostMapping
    @Operation(summary = "Crear una nueva membresia")
    public ResponseEntity<MembresiaResponseDTO> crear(@Valid @RequestBody MembresiaRequestDTO request) {
        MembresiaResponseDTO creada = membresiaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una membresia por su identificador")
    public ResponseEntity<MembresiaResponseDTO> consultarPorId(@PathVariable Short id) {
        return ResponseEntity.ok(membresiaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las membresias, incluidas las dadas de baja")
    public ResponseEntity<List<MembresiaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(membresiaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las membresias activas")
    public ResponseEntity<List<MembresiaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(membresiaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una membresia existente")
    public ResponseEntity<MembresiaResponseDTO> actualizar(@PathVariable Short id, @Valid @RequestBody MembresiaRequestDTO request) {
        return ResponseEntity.ok(membresiaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una membresia")
    public ResponseEntity<Void> eliminar(@PathVariable Short id) {
        membresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
