package com.amazons3.images.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazons3.images.entity.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
