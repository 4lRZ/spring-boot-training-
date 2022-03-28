package com.alrz.cursomc.services;

import com.alrz.cursomc.dto.ClienteDTO;
import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.repositories.ClienteRepository;
import com.alrz.cursomc.services.exceptions.DataIntegrityException;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    

    public void update(ClienteEntity obj) {
        ClienteEntity newObj = find(obj.getId());
        updateData(newObj, obj);
        REPOSITORY.save(newObj);
    }

    public void delete(Long id) {
        find(id);
        try {
            REPOSITORY.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um(a) Cliente que possui entidades relacionadas");
        }
    }

    public List<ClienteDTO> findAll() {
        List<ClienteEntity> list = REPOSITORY.findAll();
        return list.stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        Page<ClienteEntity> list = REPOSITORY.findAll(pageRequest);
        return list.map(ClienteDTO::new);
    }

    public ClienteEntity fromDTO(ClienteDTO objDto) {
        return new ClienteEntity(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(ClienteEntity newObj, ClienteEntity obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
