package com.alrz.cursomc.services;

import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.repositories.ClienteRepository;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    final ClienteRepository REPOSITORY;

    public ClienteService(ClienteRepository repository) {
        REPOSITORY = repository;
    }

    public ClienteEntity find(Long id) {
        Optional<ClienteEntity> find = REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ClienteEntity.class.getName()));
    }
}
