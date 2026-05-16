package com.gym.api.service.impl;

import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;
import com.gym.api.entity.Persona;
import com.gym.api.entity.Rol;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ConflictException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.util.UsuarioMapper;
import com.gym.api.repository.PersonaRepository;
import com.gym.api.repository.RolRepository;
import com.gym.api.repository.UsuarioRepository;
import com.gym.api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementacion del contrato definido en UsuarioService.
 *
 * Esta es la primera implementacion del proyecto que tiene complejidad
 * sustancial mas alla del CRUD basico. Coordina varias responsabilidades
 * a la vez:
 *
 *   Validacion de unicidad del nombre de cuenta antes de crear o actualizar
 *   Hasheado seguro de la contraseña con BCrypt antes de almacenarla
 *   Resolucion de las relaciones con Persona y Rol a partir de sus IDs
 *   Aplicacion de las reglas de auditoria automatica (fechas y habilitado)
 *
 * Para cumplir estas responsabilidades, el servicio inyecta varios
 * componentes: el repositorio de usuarios, los repositorios de Persona y
 * Rol para resolver relaciones, el mapper para conversiones, y el
 * PasswordEncoder para el hasheado. Es un buen ejemplo de como un servicio
 * con responsabilidades reales necesita coordinar multiples colaboradores,
 * lo cual justifica la separacion de capas que diseñamos al inicio del
 * proyecto.
 *
 * El metodo crear sigue el patron "fail fast": las validaciones se hacen
 * en orden de probabilidad y costo, fallando lo antes posible si algo
 * va mal, para no gastar recursos en operaciones que van a fracasar.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario. El metodo aplica las siguientes validaciones y
     * transformaciones en orden:
     *
     * Primero verifica que el nombre de cuenta no este ya en uso. Si existe
     * otro usuario con el mismo nombre, lanza ConflictException (HTTP 409).
     *
     * Segundo, busca la persona referenciada por idPersona. Si no existe,
     * lanza ResourceNotFoundException (HTTP 404).
     *
     * Tercero, busca el rol referenciado por idRol. Si no existe, lanza
     * ResourceNotFoundException tambien.
     *
     * Solo cuando todas las validaciones pasan, procede a construir la
     * entidad, hashear la contraseña con BCrypt, asignar las relaciones,
     * y persistirla.
     */
    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByUsuario(request.getUsuario())) {
            throw new ConflictException(
                    "Ya existe un usuario con el nombre de cuenta: " + request.getUsuario()
            );
        }

        Persona persona = personaRepository.findById(request.getIdPersona())
                .orElseThrow(() -> new ResourceNotFoundException("Persona", request.getIdPersona()));

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol", request.getIdRol()));

        Usuario entidad = usuarioMapper.toEntity(request);
        entidad.setPassword(passwordEncoder.encode(request.getPassword()));
        entidad.setPersona(persona);
        entidad.setRol(rol);
        entidad.setHabilitado(true);
        entidad.setFechaAlta(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(entidad);
        return usuarioMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO consultarPorId(Integer id) {
        Usuario entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return usuarioMapper.toResponseDTO(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarHabilitados() {
        return usuarioRepository.findAll()
                .stream()
                .filter(Usuario::getHabilitado)
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    /**
     * Actualiza un usuario existente. La logica es similar a la de crear,
     * con dos diferencias importantes. Primero, la validacion de unicidad
     * del nombre de cuenta solo aplica si el nombre realmente cambio (de lo
     * contrario rechazariamos cualquier actualizacion del usuario porque
     * "ya existe"). Segundo, si el cliente envia una contraseña nueva, esta
     * se hashea y reemplaza la actual; si no envia contraseña (o envia
     * vacio), conservamos la actual.
     *
     * Para nuestro caso, dado que el DTO tiene @NotBlank en password, no
     * podemos recibir password vacio o nulo. Esto significa que cada
     * actualizacion exige una contraseña nueva. Esta limitacion la podemos
     * abordar en el futuro creando un DTO separado para actualizacion sin
     * password obligatorio, o un endpoint dedicado para cambiar contraseña.
     */
    @Override
    public UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO request) {
        Usuario entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        if (!entidad.getUsuario().equals(request.getUsuario())
                && usuarioRepository.existsByUsuario(request.getUsuario())) {
            throw new ConflictException(
                    "Ya existe otro usuario con el nombre de cuenta: " + request.getUsuario()
            );
        }

        Persona persona = personaRepository.findById(request.getIdPersona())
                .orElseThrow(() -> new ResourceNotFoundException("Persona", request.getIdPersona()));

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol", request.getIdRol()));

        usuarioMapper.updateEntity(entidad, request);
        entidad.setPassword(passwordEncoder.encode(request.getPassword()));
        entidad.setPersona(persona);
        entidad.setRol(rol);

        Usuario actualizado = usuarioRepository.save(entidad);
        return usuarioMapper.toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {
        Usuario entidad = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        entidad.setHabilitado(false);
        entidad.setFechaBaja(LocalDateTime.now());

        usuarioRepository.save(entidad);
    }
}
