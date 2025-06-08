package br.com.fiap.api_gs.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.relatorio.RelatorioRequest;
import br.com.fiap.api_gs.dto.relatorio.RelatorioRequestDrone;
import br.com.fiap.api_gs.dto.relatorio.RelatorioRequestUsuario;
import br.com.fiap.api_gs.dto.relatorio.RelatorioResponse;
import br.com.fiap.api_gs.entity.Cidade;
import br.com.fiap.api_gs.entity.Drone;
import br.com.fiap.api_gs.entity.Relatorio;
import br.com.fiap.api_gs.entity.usuario.Usuario;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.CidadeRepository;
import br.com.fiap.api_gs.repository.DroneRepository;
import br.com.fiap.api_gs.repository.RelatorioRepository;
import br.com.fiap.api_gs.repository.UsuarioRepository;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RelatorioResponse save(RelatorioRequest relatorioRequest) {
        Relatorio relatorio = relatorioRepository.save(toRelatorio(relatorioRequest));
        return toResponse(relatorio);
    }

    public Page<RelatorioResponse> findAllFiltered(String cidade, Long usuarioId, Long droneId, int pageNumber,
            int pageSize, String sort) {
        Specification<Relatorio> spec = null;

        if (cidade != null) {
            Specification<Relatorio> cidadeSpec = (root, query, cb) -> cb.equal(root.get("cidade").get("nome"), cidade);
            spec = (spec == null) ? cidadeSpec : spec.and(cidadeSpec);
        }

        if (usuarioId != null || droneId != null) {
            Specification<Relatorio> userOrDroneSpec = null;

            if (usuarioId != null) {
                Specification<Relatorio> usuarioSpec = (root, query, cb) -> cb.equal(root.get("usuario").get("id"),
                        usuarioId);
                userOrDroneSpec = (userOrDroneSpec == null) ? usuarioSpec : userOrDroneSpec.or(usuarioSpec);
            }
            if (droneId != null) {
                Specification<Relatorio> droneSpec = (root, query, cb) -> cb.equal(root.get("drone").get("id"),
                        droneId);
                userOrDroneSpec = (userOrDroneSpec == null) ? droneSpec : userOrDroneSpec.or(droneSpec);
            }

            spec = (spec == null) ? userOrDroneSpec : spec.and(userOrDroneSpec);
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<Relatorio> page = relatorioRepository.findAll(spec, pageable);

        return page.map(this::toResponse);
    }

    public RelatorioResponse findById(Long id) {
        Optional<Relatorio> relatorio = relatorioRepository.findById(id);
        return toResponse(relatorio.get());
    }

    public RelatorioResponse update(Long id, RelatorioRequest relatorioRequest) {
        Optional<Relatorio> relatorio = relatorioRepository.findById(id);
        if (relatorio.isEmpty()) {
            throw new NotFoundException("Relatório não encontrado");
        }

        Optional<Cidade> cidade = cidadeRepository.findById(relatorioRequest.getCidadeId());
        if (cidade.isEmpty()) {
            throw new NotFoundException("Cidade com esse Id não foi encontrada");
        }

        Relatorio relatorioUpdate = relatorio.get();
        relatorioUpdate.setCidade(cidade.get());
        relatorioUpdate.setDescricao(relatorioRequest.getDescricao());

        relatorioRepository.save(relatorioUpdate);

        return toResponse(relatorioUpdate);
    }

    public void delete(Long id) {
        Optional<Relatorio> relatorio = relatorioRepository.findById(id);
        if (relatorio.isEmpty()) {
            throw new NotFoundException("Relatório não encontrado");
        }

        relatorioRepository.delete(relatorio.get());
    }

    private Relatorio toRelatorio(RelatorioRequest relatorioRequest) {
        Optional<Cidade> cidade = cidadeRepository.findById(relatorioRequest.getCidadeId());
        if (cidade.isEmpty()) {
            throw new NotFoundException("Cidade com esse Id não foi encontrada");
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setCidade(cidade.get());
        relatorio.setDescricao(relatorioRequest.getDescricao());
        relatorio.setData(LocalDateTime.now());

        if (relatorioRequest instanceof RelatorioRequestUsuario relatorioRequestUsuario) {
            Optional<Usuario> usuario = usuarioRepository.findById(relatorioRequestUsuario.getUsuarioId());
            if (usuario.isEmpty()) {
                throw new NotFoundException("Usuário com esse Id não foi encontrado");
            }

            relatorio.setUsuario(usuario.get());
        } else if (relatorioRequest instanceof RelatorioRequestDrone relatorioRequestDrone) {
            Optional<Drone> drone = droneRepository.findById(relatorioRequestDrone.getDroneId());
            if (drone.isEmpty()) {
                throw new NotFoundException("Drone com esse Id não foi encontrado");
            }

            relatorio.setDrone(drone.get());
        } else {
            throw new IllegalArgumentException("Tipo de relatório desconhecido");
        }

        return relatorio;
    }

    private RelatorioResponse toResponse(Relatorio relatorio) {
        RelatorioResponse relatorioResponse = new RelatorioResponse();
        relatorioResponse.setId(relatorio.getId());
        relatorioResponse.setCidade(relatorio.getCidade().getNome());
        relatorioResponse.setDescricao(relatorio.getDescricao());
        relatorioResponse.setData(relatorio.getData());

        if (relatorio.getUsuario() != null) {
            relatorioResponse.setEmailUsuario(relatorio.getUsuario().getEmail());
        }

        if (relatorio.getDrone() != null) {
            relatorioResponse.setModeloDrone(relatorio.getDrone().getModelo());
        }

        return relatorioResponse;
    }

}
