package com.gym.api.controller;

import com.gym.api.dto.request.DiaRequestDTO;
import com.gym.api.dto.response.DiaResponseDTO;
import com.gym.api.service.DiaService;
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
@RequestMapping("/api/dias")
@RequiredArgsConstructor
@Tag(name = "Dias", description = "Gestion del catalogo de dias de la semana")
public class DiaController {

    private final DiaService diaService;

    @PostMapping
    @Operation(summary = "Crear un nuevo dia")
    public ResponseEntity<DiaResponseDTO> crear(@Valid @RequestBody DiaRequestDTO request) {
        DiaResponseDTO creado = diaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un dia por su identificador")
    public ResponseEntity<DiaResponseDTO> consultarPorId(@PathVariable Byte id) {
        return ResponseEntity.ok(diaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los dias, incluidos los dados de baja")
    public ResponseEntity<List<DiaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(diaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los dias activos")
    public ResponseEntity<List<DiaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(diaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un dia existente")
    public ResponseEntity<DiaResponseDTO> actualizar(@PathVariable Byte id, @Valid @RequestBody DiaRequestDTO request) {
        return ResponseEntity.ok(diaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un dia")
    public ResponseEntity<Void> eliminar(@PathVariable Byte id) {
        diaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
