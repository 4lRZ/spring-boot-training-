package com.alrz.cursomc.services;

import com.alrz.cursomc.dto.CategoriaDTO;
import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import com.alrz.cursomc.services.exceptions.DataIntegrityException;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void update(CategoriaEntity obj) {
        CategoriaEntity newObj = find(obj.getId());
        updateData(newObj, obj);
        REPOSITORY.save(newObj);
    }

    public void delete(Long id) {
        find(id);
        try {
            REPOSITORY.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associado");
        }
    }

    public List<CategoriaDTO> findAll() {
        List<CategoriaEntity> list = REPOSITORY.findAll();
        return list.stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    public Page<CategoriaDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        Page<CategoriaEntity> list = REPOSITORY.findAll(pageRequest);
        return list.map(CategoriaDTO::new);
    }

    public CategoriaEntity fromDTO(CategoriaDTO objDto) {
        return new CategoriaEntity(objDto.getId(), objDto.getNome());
    }

    private void updateData(CategoriaEntity newObj, CategoriaEntity obj) {
        newObj.setNome(obj.getNome());
    }

}
