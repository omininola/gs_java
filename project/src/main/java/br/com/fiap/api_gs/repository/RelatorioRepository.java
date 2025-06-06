package br.com.fiap.api_gs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.api_gs.entity.Relatorio;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long>, JpaSpecificationExecutor<Relatorio> {
}
