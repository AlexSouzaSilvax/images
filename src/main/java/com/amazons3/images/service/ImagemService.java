package com.amazons3.images.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amazons3.images.dto.ImagemDTO;
import com.amazons3.images.entity.Imagem;
import com.amazons3.images.repository.ImagemRepository;

@Service
public class ImagemService {

    private final S3Service s3Service;
    private final ImagemRepository imagemRepository;

    public ImagemService(S3Service s3Service, ImagemRepository imagemRepository) {
        this.s3Service = s3Service;
        this.imagemRepository = imagemRepository;
    }

    public String save(ImagemDTO request) throws IOException {
        // LOG UPLOAD IMAGEM PARA S3.
        String imageUrl = s3Service.uploadBase64Image(request.nome(), request.base64());
        // UPLOAD FEITO COM SUCESSO.

        // SALVANDO OBJ IMAGEM NO DB
        Imagem imagem = new Imagem();
        imagem.setUrl(imageUrl);
        imagem.setDescricao(request.descricao());
        imagem.setProjeto(request.projeto());

        imagemRepository.save(imagem);
        // OBJ IMAGEM SALVO COM SUCESSO.
        // IMPRIME O OBJETO SALVO NA BASE.
        return imageUrl;
    }

    public Optional<Imagem> findById(Long id) {
        return imagemRepository.findById(id);
    }

    public List<Imagem> findAll() {
        return imagemRepository.findAll();
    }
}
