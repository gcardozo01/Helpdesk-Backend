package com.guilherme.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
