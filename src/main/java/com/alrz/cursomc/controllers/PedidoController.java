package com.alrz.cursomc.controllers;

import com.alrz.cursomc.dto.CategoriaDTO;
import com.alrz.cursomc.entities.CategoriaEntity;
import com.alrz.cursomc.entities.PedidoEntity;
import com.alrz.cursomc.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    final PedidoService SERVICE;

    public PedidoController(PedidoService service) {
        SERVICE = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PedidoEntity> findById(@PathVariable Long id) {
        PedidoEntity obj = SERVICE.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PedidoEntity obj) {
        obj = SERVICE.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
