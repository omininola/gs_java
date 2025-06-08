package br.com.fiap.api_gs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.api_gs.dto.sensor.SensorRequest;
import br.com.fiap.api_gs.dto.sensor.SensorResponse;
import br.com.fiap.api_gs.entity.Drone;
import br.com.fiap.api_gs.entity.Sensor;
import br.com.fiap.api_gs.exception.NotFoundException;
import br.com.fiap.api_gs.repository.DroneRepository;
import br.com.fiap.api_gs.repository.SensorRepository;

@Service
public class SensorService {

  @Autowired
  private SensorRepository sensorRepository;

  @Autowired
  private DroneRepository droneRepository;

  public SensorResponse save(SensorRequest sensorRequest) {
    Sensor sensor = sensorRepository.save(toSensor(sensorRequest));
    return toResponse(sensor);
  }

  public Page<SensorResponse> findAll(int pageNumber, int pageSize, String sort) {
    Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

    Page<Sensor> sensores = sensorRepository.findAll(page);
    return sensores.map(this::toResponse);
  }

  public SensorResponse findById(Long id) {
    Optional<Sensor> sensor = sensorRepository.findById(id);

    if (sensor.isEmpty()) {
      throw new NotFoundException("Sensor não encontrado");
    }

    return toResponse(sensor.get());
  }

  public Sensor toSensor(SensorRequest sensorRequest) throws NotFoundException {
    Optional<Drone> drone = droneRepository.findById(sensorRequest.getDroneId());

    if (drone.isEmpty()) {
      throw new NotFoundException("Drone com esse Id não foi encontrado");
    }

    Sensor sensor = new Sensor();
    sensor.setDrone(drone.get());
    sensor.setTipo(sensorRequest.getTipo());
    sensor.setStatus(sensorRequest.getStatus());
    sensor.setDescricao(sensorRequest.getDescricao());
    return sensor;
  }

  public SensorResponse toResponse(Sensor sensor) {
    SensorResponse sensorResponse = new SensorResponse();
    sensorResponse.setId(sensor.getId());
    sensorResponse.setDroneId(sensor.getDrone().getId());
    sensorResponse.setTipo(sensor.getTipo());
    sensorResponse.setStatus(sensor.getStatus());
    sensorResponse.setDescricao(sensor.getDescricao());

    return sensorResponse;
  }
}
