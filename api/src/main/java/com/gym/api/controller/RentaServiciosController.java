package com.gym.api.controller;

import com.gym.api.dto.request.RentaServiciosRequestDTO;
import com.gym.api.dto.response.RentaServiciosResponseDTO;
import com.gym.api.service.RentaServiciosService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de rentas
 * de servicios.
 */
@RestController
@RequestMapping("/api/rentas-servicios")
@RequiredArgsConstructor
@Tag(name = "Rentas de Servicios", description = "Gestion de rentas comerciales de equipo del gimnasio")
public class RentaServiciosController {

    private final RentaServiciosService rentaServiciosService;

    @PostMapping
    @Operation(summary = "Registrar una nueva renta de servicios")
    public ResponseEntity<RentaServiciosResponseDTO> crear(@Valid @RequestBody RentaServiciosRequestDTO request) {
        RentaServiciosResponseDTO creada = rentaServiciosService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una renta por su identificador")
    public ResponseEntity<RentaServiciosResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(rentaServiciosService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las rentas, incluidas las dadas de baja")
    public ResponseEntity<List<RentaServiciosResponseDTO>> listarTodos() {
        return ResponseEntity.ok(rentaServiciosService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las rentas activas")
    public ResponseEntity<List<RentaServiciosResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(rentaServiciosService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una renta existente")
    public ResponseEntity<RentaServiciosResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody RentaServiciosRequestDTO request) {
        return ResponseEntity.ok(rentaServiciosService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar logicamente una renta")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        rentaServiciosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
