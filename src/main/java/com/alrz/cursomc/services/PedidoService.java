package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.PedidoEntity;
import com.alrz.cursomc.repositories.PedidoRepository;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    final PedidoRepository REPOSITORY;

    public PedidoService(PedidoRepository repository) {
        REPOSITORY = repository;
    }

    public PedidoEntity find(Long id) {
        Optional<PedidoEntity> find = REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + PedidoEntity.class.getName()));
    }
}
