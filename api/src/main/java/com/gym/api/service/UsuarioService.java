package com.gym.api.service;

import com.gym.api.dto.request.LoginRequestDTO;
import com.gym.api.dto.request.UsuarioRequestDTO;
import com.gym.api.dto.response.LoginResponseDTO;
import com.gym.api.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO request);

    UsuarioResponseDTO consultarPorId(Integer id);

    List<UsuarioResponseDTO> listarTodos();

    List<UsuarioResponseDTO> listarHabilitados();

    UsuarioResponseDTO actualizar(Integer id, UsuarioRequestDTO request);

    void eliminar(Integer id);

    LoginResponseDTO login(LoginRequestDTO request);
}
