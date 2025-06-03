package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.drone.DroneRequest;
import br.com.fiap.api_gs.dto.drone.DroneResponse;
import br.com.fiap.api_gs.entity.Drone;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.DroneRepository;

@Service
public class DroneService {

  @Autowired
  private DroneRepository droneRepository;

  public DroneResponse save(DroneRequest droneRequest){
    Drone drone = droneRepository.save(toDrone(droneRequest));
    return toResponse(drone);
  }

  public Page<DroneResponse> findAll(int pageNumber, int pageSize, String sort){
    Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

    Page<Drone> drones = droneRepository.findAll(page);
    return drones.map(this::toResponse);
  }

  public DroneResponse findById(Long id){
    Optional<Drone> drone = droneRepository.findById(id);

    if (drone.isEmpty()){
      throw new NotFoundException("Drone não encontrado");
    }

    return toResponse(drone.get());
  }

  public DroneResponse update(DroneRequest droneRequest, Long id) {
    Optional<Drone> drone = droneRepository.findById(id);

    if (drone.isEmpty()){
      throw new NotFoundException("Drone não encontrado");
    }

    Drone droneUpdate = drone.get();
    droneUpdate.setModelo(droneRequest.getModelo());
    droneUpdate.setStatus(droneRequest.getStatus());

    droneRepository.save(droneUpdate);

    return toResponse(droneUpdate);
  }

  public void delete(Long id){
    Optional<Drone> drone = droneRepository.findById(id);

    if (drone.isEmpty()){
      throw new NotFoundException("Drone não encontrado");
    }

    droneRepository.delete(drone.get());
  }

  private Drone toDrone(DroneRequest droneRequest){
    Drone drone = new Drone();
    drone.setModelo(droneRequest.getModelo());
    drone.setStatus(droneRequest.getStatus());

    return drone;
  }

  private DroneResponse toResponse(Drone drone){
    DroneResponse droneResponse = new DroneResponse();
    droneResponse.setId(drone.getId());
    droneResponse.setModelo(drone.getModelo());
    droneResponse.setStatus(drone.getStatus());
    droneResponse.setRelatorios(drone.getRelatorios());

    return droneResponse;
  }
}
