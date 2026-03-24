package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Contrato;
import com.manoelalmorais.alugafacil.domain.Imovel;
import com.manoelalmorais.alugafacil.domain.Inquilino;
import com.manoelalmorais.alugafacil.dto.contrato.ContratoRequestDTO;
import com.manoelalmorais.alugafacil.dto.contrato.ContratoResponseDTO;
import com.manoelalmorais.alugafacil.repository.ContratoRepository;
import com.manoelalmorais.alugafacil.repository.ImovelRepository;
import com.manoelalmorais.alugafacil.repository.InquilinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ImovelRepository imovelRepository;
    private final InquilinoRepository inquilinoRepository;

    public List<ContratoResponseDTO> getAllContrato() {
        return contratoRepository.findAll().stream().map(
                contrato -> new ContratoResponseDTO(
                        contrato.getId(),
                        contrato.getValor(),
                        contrato.getDataInicio(),
                        contrato.getDataFim(),
                        contrato.getDiaVencimento(),
                        contrato.getStatus(),
                        contrato.getObservacoes()
                )
        ).toList();
    }

    public ContratoResponseDTO getContratoById(Long id) {
        Contrato contrato = contratoRepository.findById(id).orElseThrow(() -> new RuntimeException("Contrato não encontrado" + id));

        return new ContratoResponseDTO(
                contrato.getId(),
                contrato.getValor(),
                contrato.getDataInicio(),
                contrato.getDataFim(),
                contrato.getDiaVencimento(),
                contrato.getStatus(),
                contrato.getObservacoes()
        );
    }

    public ContratoResponseDTO cadastrarContrato(ContratoRequestDTO dto){

        Imovel imovel = imovelRepository.findById(dto.imovelId())
                .orElseThrow(() -> new RuntimeException("Imovel nao encontrado"));

        Inquilino inquilino = inquilinoRepository.findById(dto.inquilinoId())
                .orElseThrow(() -> new RuntimeException("Inquilino nao encontrado"));

        Contrato contrato = new Contrato();
        contrato.setInquilino(inquilino);
        contrato.setImovel(imovel);
        contrato.setStatus(dto.status());
        contrato.setValor(dto.valor());
        contrato.setDataInicio(dto.dataInicio());
        contrato.setDataFim(dto.dataFim());
        contrato.setDiaVencimento(dto.diaVencimento());
        contrato.setObservacoes(dto.observacoes());

        Contrato salvo = contratoRepository.save(contrato);

        return new ContratoResponseDTO(
                salvo.getId(),
                salvo.getValor(),
                salvo.getDataInicio(),
                salvo.getDataFim(),
                salvo.getDiaVencimento(),
                salvo.getObservacoes(),
                salvo.getStatus()
        );
    }

    public ContratoResponseDTO updateContrato(Long id, ContratoRequestDTO dto) {

        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Inquilino não encontrado: " + id));

        contrato.setStatus(dto.status());
        contrato.setValor(dto.valor());
        contrato.setDataInicio(dto.dataInicio());
        contrato.setDataFim(dto.dataFim());
        contrato.setDiaVencimento(dto.diaVencimento());
        contrato.setObservacoes(dto.observacoes());

        Contrato salvo = contratoRepository.save(contrato);

        return new ContratoResponseDTO(
                salvo.getId(),
                salvo.getValor(),
                salvo.getDataInicio(),
                salvo.getDataFim(),
                salvo.getDiaVencimento(),
                salvo.getObservacoes(),
                salvo.getStatus()
        );
    }

    public void deleteContrato(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new RuntimeException("Contrato com ID " + id + "não encontrado");
        }
        contratoRepository.deleteById(id);
    }
}
