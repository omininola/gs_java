package br.com.fiap.api_gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api_gs.dto.auth.AuthResponse;
import br.com.fiap.api_gs.dto.usuario.UsuarioLoginRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioRequest;
import br.com.fiap.api_gs.dto.usuario.UsuarioResponse;
import br.com.fiap.api_gs.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // POST - Register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        AuthResponse authResponse = usuarioService.save(usuarioRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // POST - Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUsuario(@Valid @RequestBody UsuarioLoginRequest usuarioLoginRequest) {
        AuthResponse authResponse = usuarioService.login(usuarioLoginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // GET - All
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> readUsuarios(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sort) {
        Page<UsuarioResponse> usuarios = usuarioService.findAll(pageNumber, pageSize, sort);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // GET - Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> readUsuario(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.findById(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest,
            @PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.update(usuarioRequest, id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
