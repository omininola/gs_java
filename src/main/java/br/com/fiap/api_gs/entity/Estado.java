package br.com.fiap.api_gs.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_GS_ESTADO")

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "pais_id")
  private Pais pais;

  @Column(name = "nm_estado", nullable = false)
  private String nome;

  @OneToMany(mappedBy = "estado")
  private List<Cidade> cidades = new ArrayList<>();

}
