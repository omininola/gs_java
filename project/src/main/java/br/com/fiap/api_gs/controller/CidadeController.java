package br.com.fiap.api_gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api_gs.dto.cidade.CidadeRequest;
import br.com.fiap.api_gs.dto.cidade.CidadeResponse;
import br.com.fiap.api_gs.service.CidadeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    // POST
    @PostMapping
    public ResponseEntity<CidadeResponse> createCidade(@Valid @RequestBody CidadeRequest cidadeRequest) {
        CidadeResponse cidade = cidadeService.save(cidadeRequest);
        return new ResponseEntity<>(cidade, HttpStatus.OK);
    }

    // GET - All
    @GetMapping
    public ResponseEntity<Page<CidadeResponse>> readCidadees(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sort) {
        Page<CidadeResponse> cidades = cidadeService.findAll(pageNumber, pageSize, sort);
        return new ResponseEntity<>(cidades, HttpStatus.OK);
    }

    // GET - Id
    @GetMapping("/{id}")
    public ResponseEntity<CidadeResponse> readCidade(@PathVariable Long id) {
        CidadeResponse cidade = cidadeService.findById(id);
        return new ResponseEntity<>(cidade, HttpStatus.OK);
    }

}
