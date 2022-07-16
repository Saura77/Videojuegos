package com.example.videojuegos.services;

import com.example.videojuegos.entities.Categoria;
import com.example.videojuegos.entities.Videojuego;
import com.example.videojuegos.repositories.RepositoryCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategoria implements ServiceBase<Categoria> {

    @Autowired
    private RepositoryCategoria repositoryCategoria;

    @Override
    @Transactional
    public List<Categoria> findAll() throws Exception {
        try {
            List<Categoria> categorias = repositoryCategoria.findAll();
            return categorias;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Categoria findById(long id) throws Exception {
        try {
            Optional<Categoria> categoria = repositoryCategoria.findById(id);
            return categoria.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Categoria saveOne(Categoria entity) throws Exception {
        try {
            Categoria categoria = repositoryCategoria.save(entity);
            return categoria;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Categoria updateOne(Categoria entity, long id) throws Exception {
        try {
            Optional<Categoria> optional = Optional.of(repositoryCategoria.getById(id));
            Categoria categoria = optional.get();
            categoria = repositoryCategoria.save(entity);
            return categoria;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            if (repositoryCategoria.existsById(id)) {
                repositoryCategoria.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
