package com.manoelalmorais.alugafacil.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map<String, String> upload(MultipartFile file, String folder) {
        try {
            var result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );
            return Map.of(
                    "url",       (String) result.get("secure_url"),
                    "public_id", (String) result.get("public_id")
            );
        } catch (IOException e) {
            throw new RuntimeException("Falha no upload para o Cloudinary", e);
        }
    }

    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao deletar do Cloudinary", e);
        }
    }
}
