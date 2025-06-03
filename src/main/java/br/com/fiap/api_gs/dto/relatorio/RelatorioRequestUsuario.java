package br.com.fiap.api_gs.dto.relatorio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioRequestUsuario extends RelatorioRequest {

  @NotNull(message = "Id do usuário deve ser preenchido")
  private Long usuarioId;

}