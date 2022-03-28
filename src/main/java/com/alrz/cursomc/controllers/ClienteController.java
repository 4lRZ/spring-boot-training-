package com.alrz.cursomc.controllers;

import com.alrz.cursomc.dto.ClienteDTO;
import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.services.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Long id) {
        ClienteEntity obj = SERVICE.fromDTO(objDto);
        obj.setId(id);
        SERVICE.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        SERVICE.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> listDTO = SERVICE.findAll();
        return ResponseEntity.ok().body(listDTO);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<ClienteDTO> listDTO = SERVICE.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(listDTO);
    }

}
