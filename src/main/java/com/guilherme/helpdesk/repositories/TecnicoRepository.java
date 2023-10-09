package com.guilherme.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}