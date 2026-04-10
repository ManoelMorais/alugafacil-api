package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByDataFim(LocalDate dataFim);

    List<Contrato> findByStatus(String status);
}
