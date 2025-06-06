package br.com.fiap.api_gs.dto.usuario;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Relatorio;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioResponse {

  private Long id;
  private String nome;
  private String senha;
  private String email;
  private List<Relatorio> relatorios = new ArrayList<>();

}
