package br.com.fiap.api_gs.dto.estado;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.api_gs.entity.Cidade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoResponse {

    private Long id;
    private String nome;
    private List<Cidade> cidades = new ArrayList<>();

}
