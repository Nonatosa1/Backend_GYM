package com.gym.api.controller;

import com.gym.api.dto.request.PersonaRequestDTO;
import com.gym.api.dto.response.PersonaResponseDTO;
import com.gym.api.service.PersonaService;
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
 * Controlador REST que expone los endpoints HTTP para gestion de personas.
 *
 * Persona es la entidad base del modelo de identidad. Tener un endpoint
 * propio para gestionarla permite registrar personas independientemente
 * de si tendran o no cuenta de usuario en el sistema, lo cual respeta la
 * decision de diseño que separa identidad personal de credenciales de
 * acceso.
 */
@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Gestion de personas registradas en el sistema")
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    @Operation(summary = "Crear una nueva persona")
    public ResponseEntity<PersonaResponseDTO> crear(@Valid @RequestBody PersonaRequestDTO request) {
        PersonaResponseDTO creada = personaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una persona por su identificador")
    public ResponseEntity<PersonaResponseDTO> consultarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(personaService.consultarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las personas, incluidas las dadas de baja")
    public ResponseEntity<List<PersonaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(personaService.listarTodos());
    }

    @GetMapping("/habilitados")
    @Operation(summary = "Listar solo las personas activas")
    public ResponseEntity<List<PersonaResponseDTO>> listarHabilitados() {
        return ResponseEntity.ok(personaService.listarHabilitados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una persona existente")
    public ResponseEntity<PersonaResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PersonaRequestDTO request) {
        return ResponseEntity.ok(personaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar logicamente una persona")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
