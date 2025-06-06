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

import br.com.fiap.api_gs.dto.pais.PaisRequest;
import br.com.fiap.api_gs.dto.pais.PaisResponse;
import br.com.fiap.api_gs.service.PaisService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/paises")
public class PaisController {

  @Autowired
  private PaisService paisService;

  // POST
  @PostMapping
  public ResponseEntity<PaisResponse> createPais(@Valid @RequestBody PaisRequest paisRequest){
    PaisResponse pais = paisService.save(paisRequest);
    return new ResponseEntity<>(pais, HttpStatus.OK);
  }

  // GET - All
  @GetMapping
  public ResponseEntity<Page<PaisResponse>> readPaises(
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize,
    @RequestParam(defaultValue = "id") String sort
  ){
    Page<PaisResponse> paises = paisService.findAll(pageNumber, pageSize, sort);
    return new ResponseEntity<>(paises, HttpStatus.OK);
  }

  // GET - Id
  @GetMapping("/{id}")
  public ResponseEntity<PaisResponse> readPais(@PathVariable Long id){
    PaisResponse pais = paisService.findById(id);
    return new ResponseEntity<>(pais, HttpStatus.OK);
  }

}
