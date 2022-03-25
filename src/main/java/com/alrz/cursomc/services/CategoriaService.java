package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import com.alrz.cursomc.services.exceptions.DataIntegrityException;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
        obj.setId(null);
        return REPOSITORY.save(obj);
    }

    public CategoriaEntity update(CategoriaEntity obj) {
        find(obj.getId());
        return REPOSITORY.save(obj);
    }

    public void delete(Long id) {
        find(id);
        try {
            REPOSITORY.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associado");
        }
    }
}
