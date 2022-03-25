package com.alrz.cursomc.controllers;

import com.alrz.cursomc.entities.PedidoEntity;
import com.alrz.cursomc.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
