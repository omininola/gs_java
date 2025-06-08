package br.com.fiap.api_gs.dto.cidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidadeRequest {

    @NotNull(message = "Id do estado deve ser preenchido")
    private Long estadoId;

    @NotBlank(message = "Nome da cidade deve ser preenchido")
    @Size(max = 50, message = "Nome da cidade deve ter no máximo 50 caracteres")
    private String nome;

}
