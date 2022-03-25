package com.alrz.cursomc.controllers;

import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody CategoriaEntity obj) {
        obj = SERVICE.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
