package com.alrz.cursomc.controllers;

import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    final ClienteService SERVICE;

    public ClienteController(ClienteService service) {
        SERVICE = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClienteEntity> findById(@PathVariable Long id) {
        ClienteEntity obj = SERVICE.find(id);
        return ResponseEntity.ok().body(obj);
    }

}
