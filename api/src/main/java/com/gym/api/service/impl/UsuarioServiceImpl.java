package com.gym.api.service.impl;

import com.gym.api.dto.request.LoginRequestDTO;
import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.LoginResponseDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;
import com.gym.api.entity.Persona;
import com.gym.api.entity.Rol;
import com.gym.api.entity.Usuario;
import com.gym.api.exception.ConflictException;
import com.gym.api.exception.ResourceNotFoundException;
import com.gym.api.exception.UnauthorizedException;
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

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

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

    /**
     * Autentica un usuario verificando sus credenciales contra la base de datos.
     *
     * Por seguridad, el mensaje de error NO distingue entre "usuario no existe"
     * y "contraseña incorrecta" para no dar pistas a un atacante sobre que
     * usuarios existen en el sistema.
     */
    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new UnauthorizedException("Usuario o contraseña incorrectos");
        }

        Persona persona = usuario.getPersona();

        if (!usuario.getPersona().getHabilitado()) {
            throw new UnauthorizedException("La cuenta no esta habilitada");
        }

        String nombreCompleto = persona.getNombre() + " " + persona.getPrimerApellido()
                + (persona.getSegundoApellido() != null ? " " + persona.getSegundoApellido() : "");

        return LoginResponseDTO.builder()
                .usuario(usuario.getUsuario())
                .nombre(nombreCompleto.trim())
                .rol(usuario.getRol().getRol())
                .build();
    }
}
