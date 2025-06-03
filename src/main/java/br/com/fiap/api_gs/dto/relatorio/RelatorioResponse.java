package br.com.fiap.api_gs.dto.relatorio;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RelatorioResponse {

  private Long id;
  private String cidade;
  private String modeloDrone;
  private String emailUsuario;
  private String descricao;
  private LocalDateTime data;

}
