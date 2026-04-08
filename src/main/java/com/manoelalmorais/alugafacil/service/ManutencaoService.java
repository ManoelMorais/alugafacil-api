package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Imovel;
import com.manoelalmorais.alugafacil.domain.Manutencao;
import com.manoelalmorais.alugafacil.dto.imovel.ManutencaoRequestDTO;
import com.manoelalmorais.alugafacil.dto.imovel.ManutencaoResponseDTO;
import com.manoelalmorais.alugafacil.repository.ImovelRepository;
import com.manoelalmorais.alugafacil.repository.ManutencaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final ImovelRepository imovelRepository;

    public List<ManutencaoResponseDTO> getAllManutencoes() {
        return manutencaoRepository.findAll().stream().map(
                manutencao -> new ManutencaoResponseDTO(
                        manutencao.getId(),
                        manutencao.getDescricao(),
                        manutencao.getCusto(),
                        manutencao.getData(),
                        manutencao.getResponsavel(),
                        manutencao.getStatus()
                )
        ).toList();
    }

    public ManutencaoResponseDTO getManutencaoId(Long id) {
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Contrato não encontrado" + id));

        return new ManutencaoResponseDTO(
                manutencao.getId(),
                manutencao.getDescricao(),
                manutencao.getCusto(),
                manutencao.getData(),
                manutencao.getResponsavel(),
                manutencao.getStatus()
        );
    }

    public ManutencaoResponseDTO cadastrarManutencao(ManutencaoRequestDTO dto) {

        Imovel imovel = imovelRepository.findById(dto.imovelId())
                .orElseThrow(() -> new EntityNotFoundException("Imovel não encontrado"));

        Manutencao manutencao = new Manutencao();
        manutencao.setImovel(imovel);
        manutencao.setDescricao(dto.descricao());
        manutencao.setCusto(dto.custo());
        manutencao.setData(dto.data());
        manutencao.setResponsavel(dto.responsavel());
        manutencao.setStatus(dto.status());

        Manutencao salvo = manutencaoRepository.save(manutencao);

        return new ManutencaoResponseDTO(
                salvo.getId(),
                salvo.getDescricao(),
                salvo.getCusto(),
                salvo.getData(),
                salvo.getResponsavel(),
                salvo.getStatus()
        );
    }

    public ManutencaoResponseDTO updadeManutencao(Long id, ManutencaoRequestDTO dto) {

        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Manutenção não encontrada: " + id));

        manutencao.setDescricao(dto.descricao());
        manutencao.setCusto(dto.custo());
        manutencao.setData(dto.data());
        manutencao.setResponsavel(dto.responsavel());
        manutencao.setStatus(dto.status());

        Manutencao salvo = manutencaoRepository.save(manutencao);

        return new ManutencaoResponseDTO(
                salvo.getId(),
                salvo.getDescricao(),
                salvo.getCusto(),
                salvo.getData(),
                salvo.getResponsavel(),
                salvo.getStatus()
        );
    }

    public void deleteManutencao(Long id) {
        if (!manutencaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Manutenção com ID" + id + "não encontado");
        }
        manutencaoRepository.deleteById(id);
    }
}
