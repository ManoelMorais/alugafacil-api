package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.FotoImovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoImovelRepository extends JpaRepository<FotoImovel, Long> {
    List<FotoImovel> findByImovelIdOrderByOrdem(Long imovelId);
    void deleteAllByImovelId(Long imovelId);
}