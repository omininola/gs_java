package br.com.fiap.api_gs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.api_gs.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
