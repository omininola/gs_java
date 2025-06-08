package br.com.fiap.api_gs.dto.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {

    @NotNull(message = "Id do drone deve ser preenchido")
    private Long droneId;

    @NotBlank(message = "Tipo do sensor deve ser preenchido")
    private String tipo;

    @NotBlank(message = "Status do sensor deve ser preenchido")
    private String status;

    private String descricao;

}
