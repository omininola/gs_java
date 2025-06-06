package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.usuario.UsuarioLoginRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioResponse;
import br.com.fiap.api_gs.entity.Usuario;
import br.com.fiap.api_gs.exception.BadRequestException;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.UsuarioRepository;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  public UsuarioResponse save(UsuarioRequest usuarioRequest){
    Usuario usuario = usuarioRepository.save(toUsuario(usuarioRequest));
    return toResponse(usuario);
  }

  public UsuarioResponse login(UsuarioLoginRequest usuarioLoginRequest){
    Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLoginRequest.getEmail());

    if (usuario.isEmpty()) {
      throw new NotFoundException("Usuário não encontrado");
    }

    if (!usuario.get().getSenha().equals(usuarioLoginRequest.getSenha())){
      throw new BadRequestException("Email ou senha incorretos");
    }

    return toResponse(usuario.get());
  }

  public Page<UsuarioResponse> findAll(int pageNumber, int pageSize, String sort){
    Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

    Page<Usuario> usuarios = usuarioRepository.findAll(page);
    return usuarios.map(this::toResponse);
  }

  public UsuarioResponse findById(Long id){
    Optional<Usuario> usuario = usuarioRepository.findById(id);

    if (usuario.isEmpty()){
      throw new NotFoundException("Usuário não encontrado");
    }

    return toResponse(usuario.get());
  }

  public UsuarioResponse update(UsuarioRequest usuarioRequest, Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);

    if (usuario.isEmpty()){
      throw new NotFoundException("Usuário não encontrado");
    }

    Usuario usuarioUpdate = usuario.get();
    usuarioUpdate.setNome(usuarioRequest.getNome());
    usuarioUpdate.setEmail(usuarioRequest.getEmail());
    usuarioUpdate.setSenha(usuarioRequest.getSenha());

    usuarioRepository.save(usuarioUpdate);

    return toResponse(usuarioUpdate);
  }

  public void delete(Long id){
    Optional<Usuario> usuario = usuarioRepository.findById(id);

    if (usuario.isEmpty()){
      throw new NotFoundException("Usuário não encontrado");
    }

    usuarioRepository.delete(usuario.get());
  }

  private Usuario toUsuario(UsuarioRequest usuarioRequest){
    Usuario usuario = new Usuario();
    usuario.setNome(usuarioRequest.getNome());
    usuario.setSenha(usuarioRequest.getSenha());
    usuario.setEmail(usuarioRequest.getEmail());

    return usuario;
  }

  private UsuarioResponse toResponse(Usuario usuario){
    UsuarioResponse usuarioResponse = new UsuarioResponse();
    usuarioResponse.setId(usuario.getId());
    usuarioResponse.setNome(usuario.getNome());
    usuarioResponse.setSenha(usuario.getSenha());
    usuarioResponse.setEmail(usuario.getEmail());
    usuarioResponse.setRelatorios(usuario.getRelatorios());

    return usuarioResponse;
  }
}
