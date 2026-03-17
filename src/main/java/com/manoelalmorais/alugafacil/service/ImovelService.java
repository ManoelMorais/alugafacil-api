package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Imovel;
import com.manoelalmorais.alugafacil.domain.Usuario;
import com.manoelalmorais.alugafacil.dto.ImovelRequestDTO;
import com.manoelalmorais.alugafacil.dto.ImovelResponseDTO;
import com.manoelalmorais.alugafacil.repository.ImovelRepository;
import com.manoelalmorais.alugafacil.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ImovelResponseDTO> getAllImoveis(){
        return imovelRepository.findAll()
                .stream()
                .map(imovel -> new ImovelResponseDTO(
                        imovel.getId(),
                        imovel.getTitulo(),
                        imovel.getEstado(),
                        imovel.getCep(),
                        imovel.getCidade(),
                        imovel.getEndereco(),
                        imovel.getBairro(),
                        imovel.getTipo(),
                        imovel.getValor(),
                        imovel.getQuartos(),
                        imovel.getBanheiro(),
                        imovel.getDescricao(),
                        imovel.getStatus(),
                        imovel.getVisivelPublico()
                ))
                .toList();
    }

    public ImovelResponseDTO getImovelByID(Long id){
        Imovel imovel =  imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imovel nao encontrado: " + id));

        return new ImovelResponseDTO(
                imovel.getId(),
                imovel.getTitulo(),
                imovel.getEstado(),
                imovel.getCep(),
                imovel.getCidade(),
                imovel.getEndereco(),
                imovel.getBairro(),
                imovel.getTipo(),
                imovel.getValor(),
                imovel.getQuartos(),
                imovel.getBanheiro(),
                imovel.getDescricao(),
                imovel.getStatus(),
                imovel.getVisivelPublico()
        );
    }

    public ImovelResponseDTO cadastrarImovel(ImovelRequestDTO dto) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        Imovel imovel = new Imovel();
        imovel.setUsuario(usuario);
        imovel.setTitulo(dto.titulo());
        imovel.setEstado(dto.estado());
        imovel.setCep(dto.cep());
        imovel.setCidade(dto.cidade());
        imovel.setEndereco(dto.endereco());
        imovel.setBairro(dto.bairro());
        imovel.setTipo(dto.tipo());
        imovel.setValor(dto.valor());
        imovel.setQuartos(dto.quartos());
        imovel.setBanheiro(dto.banheiro());
        imovel.setDescricao(dto.descricao());
        imovel.setStatus(dto.status());
        imovel.setVisivelPublico(dto.visivelPublico());

        Imovel salvo = imovelRepository.save(imovel);

        return new ImovelResponseDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getEstado(),
                salvo.getCep(),
                salvo.getCidade(),
                salvo.getEndereco(),
                salvo.getBairro(),
                salvo.getTipo(),
                salvo.getValor(),
                salvo.getQuartos(),
                salvo.getBanheiro(),
                salvo.getDescricao(),
                salvo.getStatus(),
                salvo.getVisivelPublico()
        );
    }

    public ImovelResponseDTO updateImovel(Long id, ImovelRequestDTO dto){
        Imovel imovel = imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imovel nao encontrado: " + id));

        imovel.setTitulo(dto.titulo());
        imovel.setEstado(dto.estado());
        imovel.setCep(dto.cep());
        imovel.setCidade(dto.cidade());
        imovel.setEndereco(dto.endereco());
        imovel.setBairro(dto.bairro());
        imovel.setTipo(dto.tipo());
        imovel.setValor(dto.valor());
        imovel.setQuartos(dto.quartos());
        imovel.setBanheiro(dto.banheiro());
        imovel.setDescricao(dto.descricao());
        imovel.setStatus(dto.status());
        imovel.setVisivelPublico(dto.visivelPublico());

        Imovel salvo = imovelRepository.save(imovel);

        return new ImovelResponseDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getEstado(),
                salvo.getCep(),
                salvo.getCidade(),
                salvo.getEndereco(),
                salvo.getBairro(),
                salvo.getTipo(),
                salvo.getValor(),
                salvo.getQuartos(),
                salvo.getBanheiro(),
                salvo.getDescricao(),
                salvo.getStatus(),
                salvo.getVisivelPublico()
        );
    }

    public void deleteImovel(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new RuntimeException("Imovel com ID" + id + "não encontrado");
        }
        imovelRepository.deleteById(id);
    }

}
