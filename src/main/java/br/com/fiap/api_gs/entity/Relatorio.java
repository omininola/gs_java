package br.com.fiap.api_gs.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.fiap.api_gs.entity.usuario.Usuario;
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
@Table(name = "TB_GS_RELATORIO")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    @JsonIgnore
    private Cidade cidade;

    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = true)
    @JsonIgnore
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "desc_relatorio", nullable = false)
    private String descricao;

    @Column(name = "dt_relatorio", nullable = false)
    private LocalDateTime data;

}
