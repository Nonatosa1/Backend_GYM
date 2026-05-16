package com.gym.api.controller;

import com.gym.api.dto.request.AreaRequestDTO;
import com.gym.api.dto.response.AreaResponseDTO;
import com.gym.api.service.AreaService;
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
@RequestMapping("/api/areas")
@RequiredArgsConstructor
@Tag(name = "Areas", description = "Gestion del catalogo de areas del gimnasio")
public class AreaController {

    private final AreaService areaService;

    @PostMapping
    @Operation(summary = "Crear una nueva area")
    public ResponseEntity<AreaResponseDTO> crear(@Valid @RequestBody AreaRequestDTO request) {
        AreaResponseDTO creada = areaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un area por su identificador")
    public ResponseEntity<AreaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(areaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las areas, incluidas las dadas de baja")
    public ResponseEntity<List<AreaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(areaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las areas activas")
    public ResponseEntity<List<AreaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(areaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un area existente")
    public ResponseEntity<AreaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody AreaRequestDTO request) {
        return ResponseEntity.ok(areaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente un area")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        areaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
