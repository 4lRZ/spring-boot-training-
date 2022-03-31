package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.entities.ProdutoEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import com.alrz.cursomc.repositories.ProdutoRepository;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    final ProdutoRepository PRODUTO_REPOSITORY;
    final CategoriaRepository CATEGORIA_REPOSITORY;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoria_repository) {
        PRODUTO_REPOSITORY = repository;
        CATEGORIA_REPOSITORY = categoria_repository;
    }

    public ProdutoEntity find(Long id) {
        Optional<ProdutoEntity> find = PRODUTO_REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ProdutoEntity.class.getName()));
    }

    public Page<ProdutoEntity> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        List<CategoriaEntity> categorias = CATEGORIA_REPOSITORY.findAllById(ids);
        return PRODUTO_REPOSITORY.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
