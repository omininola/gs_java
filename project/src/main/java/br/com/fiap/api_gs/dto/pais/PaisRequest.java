package br.com.fiap.api_gs.dto.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaisRequest {

    @NotBlank(message = "Nome do país deve ser preenchido")
    @Size(max = 50, message = "Nome do país deve ter no máximo 50 caracteres")
    private String nome;

}
