package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.dto.foto.FotoImovelResponseDTO;
import com.manoelalmorais.alugafacil.domain.FotoImovel;
import com.manoelalmorais.alugafacil.domain.Imovel;
import com.manoelalmorais.alugafacil.repository.FotoImovelRepository;
import com.manoelalmorais.alugafacil.repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FotoImovelService {

    private final FotoImovelRepository  fotoRepository;
    private final ImovelRepository  imovelRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public List<FotoImovelResponseDTO> uploadFotos(Long imovelId, List<MultipartFile> arquivos) {
        Imovel imovel = imovelRepository.findById(imovelId)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado: " + imovelId));

        List<FotoImovel> existentes = fotoRepository.findByImovelIdOrderByOrdem(imovelId);
        AtomicInteger ordem = new AtomicInteger(existentes.size());

        return arquivos.stream()
                .map(arquivo -> {
                    var resultado = cloudinaryService.upload(arquivo, "alugafacil/imoveis/" + imovelId);

                    FotoImovel foto = FotoImovel.builder()
                            .url(resultado.get("url"))
                            .publicId(resultado.get("public_id"))
                            .ordem(ordem.getAndIncrement())
                            .imovel(imovel)
                            .build();

                    return toDTO(fotoRepository.save(foto));
                })
                .toList();
    }

    public List<FotoImovelResponseDTO> listarPorImovel(Long imovelId) {
        return fotoRepository.findByImovelIdOrderByOrdem(imovelId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public void deletarFoto(Long fotoId) {
        FotoImovel foto = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new EntityNotFoundException("Foto não encontrada: " + fotoId));

        cloudinaryService.delete(foto.getPublicId());
        fotoRepository.delete(foto);
    }

    @Transactional
    public void deletarTodasDoImovel(Long imovelId) {
        List<FotoImovel> fotos = fotoRepository.findByImovelIdOrderByOrdem(imovelId);
        fotos.forEach(f -> cloudinaryService.delete(f.getPublicId()));
        fotoRepository.deleteAllByImovelId(imovelId);
    }

    private FotoImovelResponseDTO toDTO(FotoImovel foto) {
        return new FotoImovelResponseDTO(foto.getId(), foto.getUrl(), foto.getOrdem());
    }
}
