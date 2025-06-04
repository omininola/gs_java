package br.com.fiap.api_gs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api_gs.dto.sensor.SensorRequest;
import br.com.fiap.api_gs.dto.sensor.SensorResponse;
import br.com.fiap.api_gs.service.SensorService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sensores")
public class SensorController {

  @Autowired
  private SensorService sensorService;

  // POST
  @PostMapping
  public ResponseEntity<SensorResponse> createSensor(@Valid @RequestBody SensorRequest sensorRequest){
    return new ResponseEntity<>(sensorService.save(sensorRequest), HttpStatus.OK);
  }

  // GET - All
  @GetMapping
  public ResponseEntity<Page<SensorResponse>> readSensores(
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize,
    @RequestParam(defaultValue = "id") String sort
  ) {
    return new ResponseEntity<>(sensorService.findAll(pageNumber, pageSize, sort), HttpStatus.OK);
  }

  // GET - Id
  @GetMapping("/{id}")
  public ResponseEntity<SensorResponse> readSensor(@PathVariable Long id){
    SensorResponse sensor = sensorService.findById(id);
    return new ResponseEntity<>(sensor, HttpStatus.OK);
  }

}
