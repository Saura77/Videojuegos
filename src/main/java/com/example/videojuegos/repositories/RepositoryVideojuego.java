package com.example.videojuegos.repositories;

import com.example.videojuegos.entities.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositoryVideojuego extends JpaRepository<Videojuego, Long> {

    //Creamos dos querys mas ademas de las que vienen por defecto del CRUD en JPA, las cuales serán para
    //buscar juegos activos y juegos activos mediante un id determinado. Usamos la manera de query,
    //al llamar este metodo ejecuta esa query en la BD y devuelve una lista de videojuegos.
    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.activo = true", nativeQuery = true)
    List<Videojuego> findAllByActivo();

    //los : los colocamos para identificar que será el que recibiremos por parametro
    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.id = :id AND videojuegos.activo = true", nativeQuery = true)
    Optional<Videojuego> findByIdAndActivo(@Param("id") long id);

    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.titulo LIKE %:titulo% AND videojuegos.activo = true", nativeQuery = true)
    List<Videojuego> findByTitulo(@Param("titulo") String titulo);
}

