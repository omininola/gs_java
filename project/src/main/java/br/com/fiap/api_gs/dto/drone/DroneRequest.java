package br.com.fiap.api_gs.dto.drone;

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
public class DroneRequest {

    @NotBlank(message = "Modelo deve ser preenchido")
    @Size(max = 20, message = "Modelo deve ter no máximo 20 caracteres")
    private String modelo;

    @NotBlank(message = "Status deve ser preenchido")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status;

}
