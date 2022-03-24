package com.alrz.cursomc.config;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.entities.ProdutoEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import com.alrz.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class Instantiation implements CommandLineRunner {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        ProdutoEntity p1 = new ProdutoEntity(null, "Computador", 2000.00);
        ProdutoEntity p2 = new ProdutoEntity(null, "Impressora", 800.00);
        ProdutoEntity p3 = new ProdutoEntity(null, "Mouse", 80.00);
        CategoriaEntity cat1 = new CategoriaEntity(null, "Informática");
        CategoriaEntity cat2 = new CategoriaEntity(null, "Escritório");

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(List.of(p2));
        p1.getCategorias().addAll(List.of(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(List.of(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));


    }
}
