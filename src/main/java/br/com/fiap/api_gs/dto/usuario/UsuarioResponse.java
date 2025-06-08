package br.com.fiap.api_gs.dto.usuario;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Relatorio;
import br.com.fiap.api_gs.entity.usuario.UsuarioRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private UsuarioRole role;
    private List<Relatorio> relatorios = new ArrayList<>();

}
