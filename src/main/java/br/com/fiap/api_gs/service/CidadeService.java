package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.cidade.CidadeRequest;
import br.com.fiap.api_gs.dto.cidade.CidadeResponse;
import br.com.fiap.api_gs.entity.Cidade;
import br.com.fiap.api_gs.entity.Estado;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.CidadeRepository;
import br.com.fiap.api_gs.repository.EstadoRepository;

@Service
public class CidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private EstadoRepository estadoRepository;

  public CidadeResponse save(CidadeRequest cidadeRequest){
    Cidade cidade = cidadeRepository.save(toCidade(cidadeRequest));
    return toResponse(cidade);
  }

  public Page<CidadeResponse> findAll(int pageNumber, int pageSize, String sort){
    Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

    Page<Cidade> cidades = cidadeRepository.findAll(page);
    return cidades.map(this::toResponse);
  }

  public CidadeResponse findById(Long id){
    Optional<Cidade> cidade = cidadeRepository.findById(id);
    
    if (cidade.isEmpty()){
      throw new NotFoundException("Cidade não encontrada");
    }
    
    return toResponse(cidade.get());
  }

  private Cidade toCidade(CidadeRequest cidadeRequest){
    Optional<Estado> estado = estadoRepository.findById(cidadeRequest.getEstadoId());
    if (estado.isEmpty()) {
      throw new NotFoundException("Estado com esse Id não foi encontrado");
    }

    Cidade cidade = new Cidade();
    cidade.setEstado(estado.get());
    cidade.setNome(cidadeRequest.getNome());
    return cidade;
  }

  private CidadeResponse toResponse(Cidade cidade){
    CidadeResponse cidadeResponse = new CidadeResponse();
    cidadeResponse.setId(cidade.getId());
    cidadeResponse.setNome(cidade.getNome());
    cidadeResponse.setRelatorios(cidade.getRelatorios());
    return cidadeResponse;
  }
}
