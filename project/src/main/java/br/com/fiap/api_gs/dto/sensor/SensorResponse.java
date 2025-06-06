package br.com.fiap.api_gs.dto.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SensorResponse {

  private Long id;
  private Long droneId;
  private String tipo;
  private String status;
  private String descricao;

}
