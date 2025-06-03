package br.com.fiap.api_gs.dto.pais;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaisResponse {

  private Long id;
  private String nome;
  private List<Estado> estados = new ArrayList<>();

}
