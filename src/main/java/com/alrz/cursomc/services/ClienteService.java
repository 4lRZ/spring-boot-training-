package com.alrz.cursomc.services;

import com.alrz.cursomc.dto.ClienteDTO;
import com.alrz.cursomc.dto.ClienteNewDTO;
import com.alrz.cursomc.entities.CidadeEntity;
import com.alrz.cursomc.entities.ClienteEntity;
import com.alrz.cursomc.entities.EnderecoEntity;
import com.alrz.cursomc.entities.enums.TipoCliente;
import com.alrz.cursomc.repositories.ClienteRepository;
import com.alrz.cursomc.repositories.EnderecoRepository;
import com.alrz.cursomc.services.exceptions.DataIntegrityException;
import com.alrz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    final ClienteRepository CLIENTE_REPOSITORY;
    final EnderecoRepository ENDERECO_REPOSITORY;

    public ClienteService(ClienteRepository repository, EnderecoRepository endrepository) {
        CLIENTE_REPOSITORY = repository;
        ENDERECO_REPOSITORY = endrepository;
    }

    public ClienteEntity find(Long id) {
        Optional<ClienteEntity> find = CLIENTE_REPOSITORY.findById(id);
        return find.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ClienteEntity.class.getName()));
    }

    @Transactional
    public ClienteEntity insert(ClienteEntity obj) {
        obj.setId(null);
        obj = CLIENTE_REPOSITORY.save(obj);
        ENDERECO_REPOSITORY.saveAll(obj.getEnderecos());
        return obj;
    }

    public void update(ClienteEntity obj) {
        ClienteEntity newObj = find(obj.getId());
        updateData(newObj, obj);
        CLIENTE_REPOSITORY.save(newObj);
    }

    public void delete(Long id) {
        find(id);
        try {
            CLIENTE_REPOSITORY.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
        }
    }

    public List<ClienteDTO> findAll() {
        List<ClienteEntity> list = CLIENTE_REPOSITORY.findAll();
        return list.stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        Page<ClienteEntity> list = CLIENTE_REPOSITORY.findAll(pageRequest);
        return list.map(ClienteDTO::new);
    }

    public ClienteEntity fromDTO(ClienteDTO objDto) {
        return new ClienteEntity(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    public ClienteEntity fromDTO(ClienteNewDTO objDto) {
        ClienteEntity cli = new ClienteEntity(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
        CidadeEntity cid = new CidadeEntity(objDto.getCidadeId(), null, null);
        EnderecoEntity end = new EnderecoEntity(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2()!=null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3()!=null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    private void updateData(ClienteEntity newObj, ClienteEntity obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
