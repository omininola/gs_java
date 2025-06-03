package br.com.fiap.api_gs.dto.drone;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Relatorio;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DroneResponse {

  private Long id;
  private String modelo;
  private String status;
  private List<Relatorio> relatorios = new ArrayList<>();

}
