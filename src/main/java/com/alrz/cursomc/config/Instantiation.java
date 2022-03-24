package com.alrz.cursomc.config;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class Instantiation implements CommandLineRunner {

    @Autowired
    CategoriaRepository repository;

    @Override
    public void run(String... args) throws Exception {
        CategoriaEntity cat1 = new CategoriaEntity(null, "Informática");
        CategoriaEntity cat2 = new CategoriaEntity(null, "Escritório");
        repository.saveAll(Arrays.asList(cat1,cat2));
    }
}
