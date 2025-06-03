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

import br.com.fiap.api_gs.dto.estado.EstadoRequest;
import br.com.fiap.api_gs.dto.estado.EstadoResponse;
import br.com.fiap.api_gs.service.EstadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

  @Autowired
  private EstadoService estadoService;

  // POST
  @PostMapping
  public ResponseEntity<EstadoResponse> createEstado(@Valid @RequestBody EstadoRequest estadoRequest){
    EstadoResponse estado = estadoService.save(estadoRequest);
    return new ResponseEntity<>(estado, HttpStatus.OK);
  }

  // GET - All
  @GetMapping
  public ResponseEntity<Page<EstadoResponse>> readEstadoes(
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize,
    @RequestParam(defaultValue = "id") String sort
  ){
    Page<EstadoResponse> estados = estadoService.findAll(pageNumber, pageSize, sort);
    return new ResponseEntity<>(estados, HttpStatus.OK);
  }

  // GET - Id
  @GetMapping("/{id}")
  public ResponseEntity<EstadoResponse> readEstado(@PathVariable Long id){
    EstadoResponse estado = estadoService.findById(id);
    return new ResponseEntity<>(estado, HttpStatus.OK);
  }

}
