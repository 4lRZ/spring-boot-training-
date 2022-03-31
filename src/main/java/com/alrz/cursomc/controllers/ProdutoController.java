package com.alrz.cursomc.controllers;

import com.alrz.cursomc.controllers.utils.URL;
import com.alrz.cursomc.dto.CategoriaDTO;
import com.alrz.cursomc.dto.ProdutoDTO;
import com.alrz.cursomc.entities.ProdutoEntity;
import com.alrz.cursomc.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    final ProdutoService SERVICE;

    public ProdutoController(ProdutoService service) {
        SERVICE = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProdutoEntity> findById(@PathVariable Long id) {
        ProdutoEntity obj = SERVICE.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Long> ids = URL.decodeLongList(categorias);
        Page<ProdutoEntity> list = SERVICE.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDTO = list.map(ProdutoDTO::new);
        return ResponseEntity.ok().body(listDTO);
    }

}
