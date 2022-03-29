package com.alrz.cursomc.repositories;

import com.alrz.cursomc.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Transactional(readOnly = true)
    ClienteEntity findByEmail(String email);
}
