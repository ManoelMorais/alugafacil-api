package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Inquilino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {

    Optional<Inquilino> findByEmail(String email);
}
