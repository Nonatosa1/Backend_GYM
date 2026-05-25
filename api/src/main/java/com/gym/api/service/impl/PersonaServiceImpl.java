package com.gym.api.service.impl;

import com.gym.api.dto.request.*;
import com.gym.api.dto.response.*;
import com.gym.api.entity.Persona;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.BusinessException;
import com.gym.api.exception.ConflictException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.repository.PagoRepository;
import com.gym.api.service.*;
import com.gym.api.util.PersonaMapper;
import com.gym.api.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.UUID;

/**
 * Implementacion del contrato definido en PersonaService.
 *
 * Sigue el mismo patron de los servicios de catalogos: gestiona el ciclo
 * CRUD completo de la entidad, aplica las reglas de auditoria automatica
 * en alta y baja, y delega la conversion entre entidad y DTOs en el
 * mapper correspondiente.
 *
 * Una nota de diseño: el metodo consultarPorId devuelve la persona sin
 * filtrar por habilitado. Esto significa que se puede consultar incluso
 * personas dadas de baja logica. Esta decision aporta flexibilidad para
 * casos de auditoria o de consultar historicos. Si en el futuro se
 * necesita que las consultas no expongan registros dados de baja, ese
 * filtrado puede hacerse facilmente añadiendo una verificacion despues
 * de obtener la entidad.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final InscripcionService inscripcionService;
    private final PagoService pagoService;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final PersonaMapper personaMapper;

    @Override
    public PersonaResponseDTO crear(RegistroRequestDTO request) {
        PersonaRequestDTO Persona = request.getPersona();
        UsuarioRequestDTO Usuario = request.getUsuario();
        InscripcionRequestDTO Inscripcion = request.getInscripcion();
        PagoRequestDTO Pago = request.getPago();

        if (personaRepository.existsByCorreo(Persona.getCorreo())) {
            throw new ConflictException(
                    "Ya existe una persona registrada con el correo: " + Persona.getCorreo()
            );
        }

        int edad = Period.between(Persona.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 16) {
            throw new BusinessException(
                    "La persona debe tener al menos 16 años para registrarse. Edad actual: " + edad
            );
        }

        Persona entidad = personaMapper.toEntity(Persona);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());
        entidad.setTokenConfirmacion(UUID.randomUUID().toString());
        entidad.setCuentaConfirmada(false);
        Persona personaSave = personaRepository.save(entidad);

        Usuario.setIdPersona(personaSave.getIdPersona());
        UsuarioResponseDTO usuarioSave = usuarioService.crear(Usuario);

        if(Usuario.getIdRol() == 4){
            Inscripcion.setIdUsuario(usuarioSave.getIdUsuario());
            InscripcionResponseDTO inscripcionSave = inscripcionService.crear(Inscripcion);

            Pago.setIdInscripcion(inscripcionSave.getIdInscripcion());
            PagoResponseDTO pagoSave = pagoService.crear(Pago);
        }

        String nombreCompleto = construirNombreCompleto(personaSave);
        emailService.enviarCorreoBienvenida(
                personaSave.getCorreo(),
                nombreCompleto,
                personaSave.getTokenConfirmacion()
        );

        return personaMapper.toResponseDTO(personaSave);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaResponseDTO consultarPorId(Integer id) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));
        return personaMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> listarTodos() {
        return personaRepository.findAll()
                .stream()
                .map(personaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> listarHabilitados() {
        return personaRepository.findAll()
                .stream()
                .filter(Persona::getHabilitado)
                .map(personaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PersonaResponseDTO actualizar(Integer id, PersonaRequestDTO request) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));

        int edad = Period.between(request.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 16) {
            throw new BusinessException(
                    "La persona debe tener al menos 16 años para registrarse. Edad actual: " + edad
            );
        }

        personaMapper.updateEntity(entidad, request);

        Persona actualizada = personaRepository.save(entidad);
        return personaMapper.toResponseDTO(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        Persona entidad = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        personaRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> buscarPorNombre(String termino) {
        return personaRepository
                .findByNombreContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCase(
                        termino, termino, termino)
                .stream()
                .map(personaMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ConfirmacionResponseDTO confirmarCuenta(String token) {
        return personaRepository.findByTokenConfirmacion(token)
                .map(persona -> {
                    if (Boolean.TRUE.equals(persona.getCuentaConfirmada())) {
                        return ConfirmacionResponseDTO.builder()
                                .exito(true)
                                .mensaje("Tu cuenta ya fue confirmada. Puedes iniciar sesion normalmente.")
                                .nombre(construirNombreCompleto(persona))
                                .build();
                    }

                    persona.setCuentaConfirmada(true);
                    //persona.setTokenConfirmacion(null);
                    personaRepository.save(persona);

                    return ConfirmacionResponseDTO.builder()
                            .exito(true)
                            .mensaje("Tu cuenta ha sido confirmada exitosamente. Ya puedes iniciar sesion.")
                            .nombre(construirNombreCompleto(persona))
                            .build();
                })
                .orElseGet(() -> ConfirmacionResponseDTO.builder()
                        .exito(false)
                        .mensaje("El enlace de confirmacion es invalido o ya expiro.")
                        .build());
    }

    private String construirNombreCompleto(Persona persona) {
        StringBuilder sb = new StringBuilder();
        sb.append(persona.getNombre()).append(" ").append(persona.getPrimerApellido());
        if (persona.getSegundoApellido() != null && !persona.getSegundoApellido().isBlank()) {
            sb.append(" ").append(persona.getSegundoApellido());
        }
        return sb.toString();
    }
}
