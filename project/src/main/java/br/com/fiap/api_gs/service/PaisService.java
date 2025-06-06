package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.pais.PaisRequest;
import br.com.fiap.api_gs.dto.pais.PaisResponse;
import br.com.fiap.api_gs.entity.Pais;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.PaisRepository;

@Service
public class PaisService {

  @Autowired
  private PaisRepository paisRepository;

  public PaisResponse save(PaisRequest paisRequest){
    Pais pais = paisRepository.save(toPais(paisRequest));
    return toResponse(pais);
  }

  public Page<PaisResponse> findAll(int pageNumber, int pageSize, String sort){
    Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

    Page<Pais> paises = paisRepository.findAll(page);
    return paises.map(this::toResponse);
  }

  public PaisResponse findById(Long id){
    Optional<Pais> pais = paisRepository.findById(id);
    
    if (pais.isEmpty()){
      throw new NotFoundException("País não encontrado");
    }
    
    return toResponse(pais.get());
  }

  private Pais toPais(PaisRequest paisRequest){
    Pais pais = new Pais();
    pais.setNome(paisRequest.getNome());
    return pais;
  }

  private PaisResponse toResponse(Pais pais){
    PaisResponse paisResponse = new PaisResponse();
    paisResponse.setId(pais.getId());
    paisResponse.setNome(pais.getNome());
    paisResponse.setEstados(pais.getEstados());
    return paisResponse;
  }
}
