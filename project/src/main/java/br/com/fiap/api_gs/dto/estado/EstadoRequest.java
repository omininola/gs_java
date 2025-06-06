package br.com.fiap.api_gs.dto.estado;

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
public class EstadoRequest {

  @NotNull(message = "Id do país deve ser preenchido")
  private Long paisId;

  @NotBlank(message = "Nome do estado deve ser preenchido")
  @Size(max = 50, message = "Nome do estado deve no máximo 50 caracteres")
  private String nome;

}
