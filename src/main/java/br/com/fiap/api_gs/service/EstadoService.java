package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.estado.EstadoRequest;
import br.com.fiap.api_gs.dto.estado.EstadoResponse;
import br.com.fiap.api_gs.entity.Estado;
import br.com.fiap.api_gs.entity.Pais;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.EstadoRepository;
import br.com.fiap.api_gs.repository.PaisRepository;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;

    public EstadoResponse save(EstadoRequest estadoRequest) {
        Estado estado = estadoRepository.save(toEstado(estadoRequest));
        return toResponse(estado);
    }

    public Page<EstadoResponse> findAll(int pageNumber, int pageSize, String sort) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

        Page<Estado> estados = estadoRepository.findAll(page);
        return estados.map(this::toResponse);
    }

    public EstadoResponse findById(Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if (estado.isEmpty()) {
            throw new NotFoundException("Estado não encontrado");
        }

        return toResponse(estado.get());
    }

    private Estado toEstado(EstadoRequest estadoRequest) {
        Optional<Pais> pais = paisRepository.findById(estadoRequest.getPaisId());
        if (pais.isEmpty()) {
            throw new NotFoundException("País com esse Id não foi encontrado");
        }

        Estado estado = new Estado();
        estado.setPais(pais.get());
        estado.setNome(estadoRequest.getNome());
        return estado;
    }

    private EstadoResponse toResponse(Estado estado) {
        EstadoResponse estadoResponse = new EstadoResponse();
        estadoResponse.setId(estado.getId());
        estadoResponse.setNome(estado.getNome());
        estadoResponse.setCidades(estado.getCidades());
        return estadoResponse;
    }
}
