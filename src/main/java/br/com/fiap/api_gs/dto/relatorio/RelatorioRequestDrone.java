package br.com.fiap.api_gs.dto.relatorio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioRequestDrone extends RelatorioRequest {

    @NotNull(message = "Id do drone deve ser preenchido")
    private Long droneId;

}
