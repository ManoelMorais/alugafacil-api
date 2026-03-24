package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    Optional<Imovel> findByTitulo(String titulo);

    List<Imovel> id(Long id);
}
