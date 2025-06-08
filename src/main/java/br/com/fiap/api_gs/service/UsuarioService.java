package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.auth.AuthResponse;
import br.com.fiap.api_gs.dto.usuario.UsuarioLoginRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioResponse;
import br.com.fiap.api_gs.entity.usuario.Usuario;
import br.com.fiap.api_gs.exception.BadRequestException;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.UsuarioRepository;
import br.com.fiap.api_gs.security.TokenService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public AuthResponse save(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.findByEmail(usuarioRequest.getEmail()) != null) {
            throw new BadRequestException("Email já cadastrado");
        }

        String rawPassword = usuarioRequest.getSenha();
        String senhaEncriptada = new BCryptPasswordEncoder().encode(rawPassword);
        usuarioRequest.setSenha(senhaEncriptada);

        Usuario usuario = usuarioRepository.save(toUsuario(usuarioRequest));

        String token = getToken(new UsuarioLoginRequest(usuarioRequest.getEmail(), rawPassword));

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUsuario(toResponse(usuario));

        return authResponse;
    }

    public AuthResponse login(UsuarioLoginRequest usuarioLoginRequest) {
        String token = getToken(usuarioLoginRequest);

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(usuarioLoginRequest.getEmail());
        if (usuario == null) {
            throw new NotFoundException("Email não encontrado");
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUsuario(toResponse(usuario));

        return authResponse;
    }

    public Page<UsuarioResponse> findAll(int pageNumber, int pageSize, String sort) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

        Page<Usuario> usuarios = usuarioRepository.findAll(page);
        return usuarios.map(this::toResponse);
    }

    public UsuarioResponse findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return toResponse(usuario.get());
    }

    public UsuarioResponse update(UsuarioRequest usuarioRequest, Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Usuario usuarioUpdate = usuario.get();
        usuarioUpdate.setNome(usuarioRequest.getNome());
        usuarioUpdate.setEmail(usuarioRequest.getEmail());
        usuarioUpdate.setRole(usuarioRequest.getRole());

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuarioRequest.getSenha());
        usuarioUpdate.setSenha(senhaEncriptada);

        usuarioRepository.save(usuarioUpdate);

        return toResponse(usuarioUpdate);
    }

    public void delete(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        usuarioRepository.delete(usuario.get());
    }

    private Usuario toUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setRole(usuarioRequest.getRole());

        return usuario;
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setRole(usuario.getRole());
        usuarioResponse.setRelatorios(usuario.getRelatorios());

        return usuarioResponse;
    }

    private String getToken(UsuarioLoginRequest usuarioLoginRequest) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                usuarioLoginRequest.getEmail(),
                usuarioLoginRequest.getSenha());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return token;
    }
}
