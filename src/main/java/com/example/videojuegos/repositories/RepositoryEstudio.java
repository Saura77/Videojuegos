package com.example.videojuegos.repositories;

import com.example.videojuegos.entities.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEstudio extends JpaRepository<Estudio, Long> {
}
