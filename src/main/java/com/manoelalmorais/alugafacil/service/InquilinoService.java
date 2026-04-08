package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Inquilino;
import com.manoelalmorais.alugafacil.domain.Usuario;
import com.manoelalmorais.alugafacil.dto.usuario.InquilinoRequestDTO;
import com.manoelalmorais.alugafacil.dto.usuario.InquilinoResponseDTO;
import com.manoelalmorais.alugafacil.repository.InquilinoRepository;
import com.manoelalmorais.alugafacil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquilinoService {

    private final InquilinoRepository inquilinoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<InquilinoResponseDTO> getAllInquilinos(){
        return inquilinoRepository.findAll()
                .stream()
                .map(inquilino -> new InquilinoResponseDTO(
                        inquilino.getId(),
                        inquilino.getNome(),
                        inquilino.getCpf(),
                        inquilino.getEmail(),
                        inquilino.getTelefone()
                ))
                .toList();
    }

    public InquilinoResponseDTO getInquilinoBy(Long id) {
        Inquilino inquilino = inquilinoRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Inquilino não encontrado: " + id));

        return new InquilinoResponseDTO(
                inquilino.getId(),
                inquilino.getNome(),
                inquilino.getCpf(),
                inquilino.getEmail(),
                inquilino.getTelefone()
        );
    }

    public InquilinoResponseDTO cadastrarInquilino(InquilinoRequestDTO dto) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Inquilino inquilino = new Inquilino();
        inquilino.setUsuario(usuario);
        inquilino.setNome(dto.nome());
        inquilino.setEmail(dto.email());
        inquilino.setCpf(dto.cpf());
        inquilino.setTelefone(dto.telefone());

        Inquilino salvo = inquilinoRepository.save(inquilino);

        return new InquilinoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCpf(),
                salvo.getEmail(),
                salvo.getTelefone()
        );
    }

    public InquilinoResponseDTO updateInquilino(Long id, InquilinoRequestDTO dto){
        Inquilino inquilino =inquilinoRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Inquilino não encontrado: " + id));

        inquilino.setNome(dto.nome());
        inquilino.setEmail(dto.email());
        inquilino.setCpf(dto.cpf());
        inquilino.setTelefone(dto.telefone());

        Inquilino salvo = inquilinoRepository.save(inquilino);

        return new InquilinoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCpf(),
                salvo.getEmail(),
                salvo.getTelefone()
        );
    }

    public void deleteInquilino(Long id){
        if (!inquilinoRepository.existsById(id)) {
            throw new EntityNotFoundException("Inquilino com ID " + id + "não encontrado");
        }
        inquilinoRepository.deleteById(id);
    }
}
