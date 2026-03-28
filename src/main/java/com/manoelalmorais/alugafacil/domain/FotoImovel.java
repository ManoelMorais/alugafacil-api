package com.manoelalmorais.alugafacil.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foto_imovel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FotoImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "public_id", nullable = false)
    private String publicId; // usado para deletar no Cloudinary

    @Column(name = "ordem")
    private Integer ordem; // para ordenar as fotos no frontend

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imovel_id", nullable = false)
    private Imovel imovel;
}
