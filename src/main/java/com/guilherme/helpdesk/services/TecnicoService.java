package com.guilherme.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.helpdesk.domain.Tecnico;
import com.guilherme.helpdesk.domain.dtos.TecnicoDTO;
import com.guilherme.helpdesk.repositories.TecnicoRepository;
import com.guilherme.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO tecnicoDTO) {
		tecnicoDTO.setId(null);
		Tecnico tecnico = new Tecnico(tecnicoDTO);
		return tecnicoRepository.save(tecnico);
	}
}
