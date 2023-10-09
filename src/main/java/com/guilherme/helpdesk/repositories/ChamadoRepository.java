package com.guilherme.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
