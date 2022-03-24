package com.alrz.cursomc.repositories;

import com.alrz.cursomc.entities.CidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeEntity, Long> {
}
