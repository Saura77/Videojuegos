package com.example.videojuegos.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceBase<E> { //Con el <E> implementamos la generalización (cualquier tipo de entidad)
    List<E> findAll() throws Exception;

    E findById(long id) throws Exception;

    E saveOne(E entity) throws Exception;

    E updateOne(E entity, long id) throws Exception;

    boolean deleteById(long id) throws Exception;
}
