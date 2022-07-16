package com.example.videojuegos.services;

import com.example.videojuegos.entities.Videojuego;
import com.example.videojuegos.repositories.RepositoryVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceVideojuego implements ServiceBase<Videojuego> {

    @Autowired
    private RepositoryVideojuego repositoryVideojuego;

    @Override
    @Transactional
    public List<Videojuego> findAll() throws Exception {
        try {
            List<Videojuego> videojuegos = repositoryVideojuego.findAll();
            return videojuegos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego findById(long id) throws Exception {
        try {
            Optional<Videojuego> videojuego = repositoryVideojuego.findById(id);
            return videojuego.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego saveOne(Videojuego entity) throws Exception {
        try {
            Videojuego videojuego = repositoryVideojuego.save(entity);
            return videojuego;
        } catch (Exception e) {
            System.out.println("Holaaa");
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego updateOne(Videojuego entity, long id) throws Exception {
        try {
            Optional<Videojuego> optional = repositoryVideojuego.findById(id);
            Videojuego videojuego = optional.get();
            videojuego = repositoryVideojuego.save(entity);
            return videojuego;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            if (repositoryVideojuego.existsById(id)) {
                repositoryVideojuego.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Videojuego> findAllByActivo() throws Exception {
        try {
            List<Videojuego> videojuegos = repositoryVideojuego.findAllByActivo();
            return videojuegos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Videojuego findByIdAndActivo(Long id) throws Exception {
        try {
            Optional<Videojuego> optional = repositoryVideojuego.findByIdAndActivo(id);
            return optional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Videojuego> findByTitulo(String titulo) throws Exception {
        try {
            List<Videojuego> videojuegos = repositoryVideojuego.findByTitulo(titulo);
            return videojuegos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
