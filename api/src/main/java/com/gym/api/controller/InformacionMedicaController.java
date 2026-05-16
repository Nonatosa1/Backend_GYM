package com.gym.api.controller;

import com.gym.api.dto.request.InformacionMedicaRequestDTO;
import com.gym.api.dto.response.InformacionMedicaResponseDTO;
import com.gym.api.service.InformacionMedicaService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de la
 * informacion medica de los usuarios.
 *
 * Este es el ultimo controlador del proyecto y maneja datos sensibles
 * de salud. En una version mas madura del sistema, estos endpoints
 * deberian estar protegidos por reglas de autorizacion estrictas que
 * limiten el acceso a personal autorizado y al propio usuario.
 */
@RestController
@RequestMapping("/api/informacion-medica")
@RequiredArgsConstructor
@Tag(name = "Informacion Medica", description = "Gestion de informacion medica sensible de los usuarios")
public class InformacionMedicaController {

    private final InformacionMedicaService informacionMedicaService;

    @PostMapping
    @Operation(summary = "Registrar informacion medica de un usuario")
    public ResponseEntity<InformacionMedicaResponseDTO> crear(@Valid @RequestBody InformacionMedicaRequestDTO request) {
        InformacionMedicaResponseDTO creada = informacionMedicaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar informacion medica por su identificador")
    public ResponseEntity<InformacionMedicaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(informacionMedicaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar toda la informacion medica, incluida la dada de baja")
    public ResponseEntity<List<InformacionMedicaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(informacionMedicaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo los registros activos")
    public ResponseEntity<List<InformacionMedicaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(informacionMedicaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar informacion medica existente")
    public ResponseEntity<InformacionMedicaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody InformacionMedicaRequestDTO request) {
        return ResponseEntity.ok(informacionMedicaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente informacion medica")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        informacionMedicaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
