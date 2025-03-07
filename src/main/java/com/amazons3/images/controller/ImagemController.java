package com.amazons3.images.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazons3.images.dto.ImagemDTO;
import com.amazons3.images.entity.Imagem;
import com.amazons3.images.service.ImagemService;

@RestController
@RequestMapping("/api/imagem")
public class ImagemController {

    @Autowired
    ImagemService imagemService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody ImagemDTO request) {
        
        try {
            return ResponseEntity.ok(imagemService.save(request));
        } catch (Exception e) {
            // LOG ERRO AO SALVAR IMAGEM
            return ResponseEntity.status(500).body("Erro ao salvar imagem: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagem> findById(@PathVariable Long id) {
        return imagemService.findById(id)
                .map(imagem -> ResponseEntity.ok(imagem))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        try {
            List<Imagem> imagens = imagemService.findAll();

            if (imagens.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma imagem encontrada.");
            }

            return ResponseEntity.ok(imagens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar imagens: " + e.getMessage());
        }
    }

}
