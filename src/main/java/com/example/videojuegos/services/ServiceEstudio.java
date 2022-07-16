package com.example.videojuegos.services;

import com.example.videojuegos.entities.Categoria;
import com.example.videojuegos.entities.Estudio;
import com.example.videojuegos.repositories.RepositoryCategoria;
import com.example.videojuegos.repositories.RepositoryEstudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceEstudio implements ServiceBase<Estudio> {

    @Autowired
    private RepositoryEstudio repositoryEstudio;

    @Override
    @Transactional
    public List<Estudio> findAll() throws Exception {
        try {
            List<Estudio> estudios = repositoryEstudio.findAll();
            return estudios;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio findById(long id) throws Exception {
        try {
            Optional<Estudio> estudio = repositoryEstudio.findById(id);
            return estudio.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio saveOne(Estudio entity) throws Exception {
        try {
            Estudio estudio = repositoryEstudio.save(entity);
            return estudio;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio updateOne(Estudio entity, long id) throws Exception {
        try {
            Optional<Estudio> optional = Optional.of(repositoryEstudio.getById(id));
            Estudio estudio = optional.get();
            estudio = repositoryEstudio.save(entity);
            return estudio;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            if (repositoryEstudio.existsById(id)) {
                repositoryEstudio.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
