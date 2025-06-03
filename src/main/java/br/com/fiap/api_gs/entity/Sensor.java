package br.com.fiap.api_gs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_GS_SENSOR")

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "drone_id")
  private Drone drone;

  @Column(name = "tipo", nullable = false)
  private String tipo;
  
  @Column(name = "nm_status", nullable = false)
  private String status;

  @Column(name = "desc_drone", nullable = true)
  private String descricao;

}
