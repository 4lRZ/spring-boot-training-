package com.alrz.cursomc.controllers;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    final CategoriaService SERVICE;

    public CategoriaController(CategoriaService service) {
        SERVICE = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CategoriaEntity> findById(@PathVariable Long id) {
        CategoriaEntity obj = SERVICE.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}
