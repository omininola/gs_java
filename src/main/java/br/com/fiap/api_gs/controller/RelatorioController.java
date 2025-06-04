package br.com.fiap.api_gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api_gs.dto.relatorio.RelatorioRequest;
import br.com.fiap.api_gs.dto.relatorio.RelatorioRequestDrone;
import br.com.fiap.api_gs.dto.relatorio.RelatorioRequestUsuario;
import br.com.fiap.api_gs.dto.relatorio.RelatorioResponse;
import br.com.fiap.api_gs.service.RelatorioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

  @Autowired
  private RelatorioService relatorioService;

  // POST - Usuario
  @PostMapping("/usuario")
  public ResponseEntity<RelatorioResponse> createRelatorioUsuario(@Valid @RequestBody RelatorioRequestUsuario relatorioRequest){
    RelatorioResponse relatorio = relatorioService.save(relatorioRequest);
    return new ResponseEntity<>(relatorio, HttpStatus.OK);
  }

  // POST - Drone
  @PostMapping("/drone")
  public ResponseEntity<RelatorioResponse> createRelatorioDrone(@Valid @RequestBody RelatorioRequestDrone relatorioRequest){
    RelatorioResponse relatorio = relatorioService.save(relatorioRequest);
    return new ResponseEntity<>(relatorio, HttpStatus.OK);
  }

  // GET - All
  @GetMapping
  public ResponseEntity<Page<RelatorioResponse>> findAll(
      @RequestParam(required = false) String cidade,
      @RequestParam(required = false) Long usuarioId,
      @RequestParam(required = false) Long droneId,
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "id") String sort
  ) {
      Page<RelatorioResponse> relatorios = relatorioService.findAllFiltered(cidade, usuarioId, droneId, pageNumber, pageSize, sort);
      return new ResponseEntity<>(relatorios, HttpStatus.OK);
  }

  // GET - Id
  @GetMapping("/{id}")
  public ResponseEntity<RelatorioResponse> findById(@PathVariable Long id){
    RelatorioResponse relatorio = relatorioService.findById(id);
    return new ResponseEntity<>(relatorio, HttpStatus.OK);
  }

  // PUT
  @PutMapping("/{id}")
  public ResponseEntity<RelatorioResponse> updateRelatorio(@PathVariable Long id, @Valid @RequestBody RelatorioRequest relatorioRequest) {
    RelatorioResponse relatorio = relatorioService.update(id, relatorioRequest);
    return new ResponseEntity<>(relatorio, HttpStatus.OK);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<RelatorioResponse> deleteRelatorio(@PathVariable Long id){
    relatorioService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
