package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.Usuario;
import com.manoelalmorais.alugafacil.security.JwtService;
import com.manoelalmorais.alugafacil.dto.LoginRequestDTO;
import com.manoelalmorais.alugafacil.dto.LoginResponseDTO;
import com.manoelalmorais.alugafacil.dto.usuario.UsuarioRequestDTO;
import com.manoelalmorais.alugafacil.dto.usuario.UsuarioResponseDTO;
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
    private final JwtService jwtService;

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

    public LoginResponseDTO login(LoginRequestDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponseDTO(token);
    }
}
