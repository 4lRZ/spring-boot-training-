package com.alrz.cursomc.repositories;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.entities.ProdutoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM ProdutoEntity obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    Page<ProdutoEntity> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<CategoriaEntity> categorias, Pageable pageRequest);
}
