package br.com.fiap.api_gs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.api_gs.entity.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}
