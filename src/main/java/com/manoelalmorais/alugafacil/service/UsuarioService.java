package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Usuario;
import com.manoelalmorais.alugafacil.dto.UsuarioRequestDTO;
import com.manoelalmorais.alugafacil.dto.UsuarioResponseDTO;
import com.manoelalmorais.alugafacil.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTelefone(dto.telefone());
        usuario.setAtivo(true);
        usuario.setPlano("BASICO");

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getPlano(),
                salvo.getAtivo()
        );
    }
}
