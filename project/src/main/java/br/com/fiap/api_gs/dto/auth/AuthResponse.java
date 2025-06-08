package br.com.fiap.api_gs.dto.auth;

import br.com.fiap.api_gs.dto.usuario.UsuarioResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    String token;
    UsuarioResponse usuario;

}
