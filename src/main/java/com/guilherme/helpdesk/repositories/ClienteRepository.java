package com.guilherme.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}