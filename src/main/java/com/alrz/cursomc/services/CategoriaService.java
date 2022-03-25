package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    final CategoriaRepository REPOSITORY;

    public CategoriaService(CategoriaRepository repository) {
        REPOSITORY = repository;
    }

    public CategoriaEntity find(Long id) {
        Optional<CategoriaEntity> find = REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + CategoriaEntity.class.getName()));
    }

    public CategoriaEntity insert(CategoriaEntity obj) {
        find(obj.getId());
        obj.setId(null);
        return REPOSITORY.save(obj);
    }

    public CategoriaEntity update(CategoriaEntity obj) {
        return REPOSITORY.save(obj);
    }
}
