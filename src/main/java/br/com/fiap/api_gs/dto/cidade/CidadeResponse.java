package br.com.fiap.api_gs.dto.cidade;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Relatorio;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CidadeResponse {

  private Long id;
  private String nome;
  private List<Relatorio> relatorios = new ArrayList<>();

}
