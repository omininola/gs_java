package br.com.fiap.api_gs.dto.relatorio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioRequest {

  @NotNull(message = "Id da cidade deve ser preenchido")
  private Long cidadeId;

  @NotBlank(message = "Descrição deve ser preenchida")
  @Size(min = 12, message = "Descrição deve ter no mínino 12 caracteres e deve ser detalhada")
  private String descricao;

}
