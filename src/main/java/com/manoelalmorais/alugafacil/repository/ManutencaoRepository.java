package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
}
