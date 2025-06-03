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

import br.com.fiap.api_gs.dto.drone.DroneRequest;
import br.com.fiap.api_gs.dto.drone.DroneResponse;
import br.com.fiap.api_gs.service.DroneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/drones")
public class DroneController {

  @Autowired
  private DroneService droneService;

  // POST
  @PostMapping
  public ResponseEntity<DroneResponse> createDrone(@Valid @RequestBody DroneRequest droneRequest){
    DroneResponse drone = droneService.save(droneRequest);
    return new ResponseEntity<>(drone, HttpStatus.OK);
  }

  // GET - All
  @GetMapping
  public ResponseEntity<Page<DroneResponse>> readDrones(
    @RequestParam(defaultValue = "0") int pageNumber,
    @RequestParam(defaultValue = "10") int pageSize,
    @RequestParam(defaultValue = "id") String sort
  ) {
    Page<DroneResponse> drones = droneService.findAll(pageNumber, pageSize, sort); 
    return new ResponseEntity<>(drones, HttpStatus.OK);
  }
  
  // GET - Id
  @GetMapping("/{id}")
  public ResponseEntity<DroneResponse> readDrone(@PathVariable Long id){
    DroneResponse drone = droneService.findById(id);
    return new ResponseEntity<>(drone, HttpStatus.OK);
  }

  // PUT
  @PutMapping("/{id}")
  public ResponseEntity<DroneResponse> updateDrone(@Valid @RequestBody DroneRequest droneRequest, @PathVariable Long id){
    DroneResponse drone = droneService.update(droneRequest, id);
    return new ResponseEntity<>(drone, HttpStatus.OK);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<DroneResponse> deleteDrone(Long id){
    droneService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
