package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
