package com.manoelalmorais.alugafacil.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "imovel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String titulo;
    private String estado;
    private String cep;
    private String cidade;
    private String endereco;
    private String bairro;
    private String tipo;
    private BigDecimal valor;
    private Integer quartos;
    private Integer banheiro;
    private String descricao;
    private String status;
    private Boolean visivelPublico;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
