package com.guilherme.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilherme.helpdesk.domain.Chamado;
import com.guilherme.helpdesk.domain.Cliente;
import com.guilherme.helpdesk.domain.Tecnico;
import com.guilherme.helpdesk.domain.dtos.ChamadoDTO;
import com.guilherme.helpdesk.domain.enums.Prioridade;
import com.guilherme.helpdesk.domain.enums.Status;
import com.guilherme.helpdesk.repositories.ChamadoRepository;
import com.guilherme.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> chamado = chamadoRepository.findById(id);
		return chamado.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! ID: " + id));
	}

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO chamadoDTO) {
		return chamadoRepository.save(newChamado(chamadoDTO));
	}
	
	public Chamado update(Integer id, @Valid ChamadoDTO chamadoDTO) {
		chamadoDTO.setId(id);
		Chamado chamado = findById(id);
		chamado = newChamado(chamadoDTO);
		return chamadoRepository.save(chamado);
	}
	
	private Chamado newChamado(ChamadoDTO chamadoDTO) {
		Tecnico tecnico = tecnicoService.findById(chamadoDTO.getTecnico());
		Cliente cliente = clienteService.findById(chamadoDTO.getCliente());
		
		Chamado chamado = new Chamado();
		
		if (chamadoDTO.getId() != null) {
			chamado.setId(chamadoDTO.getId());
		}
		
		if (chamadoDTO.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
		chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
		chamado.setTitulo(chamadoDTO.getTitulo());
		chamado.setObservacoes(chamadoDTO.getObservacoes());
		return chamado;
	}
}
