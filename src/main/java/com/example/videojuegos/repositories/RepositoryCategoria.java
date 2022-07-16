package com.example.videojuegos.repositories;

import com.example.videojuegos.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//JPA necesita como paramétros la clase de la cual se comunicará con las instancias en la base de datos, junto
//con el tipo de dato de su clave primaria
public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {

}
