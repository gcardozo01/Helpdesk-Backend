package com.guilherme.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.guilherme.helpdesk.domain.Cliente;
import com.guilherme.helpdesk.domain.Pessoa;
import com.guilherme.helpdesk.domain.dtos.ClienteDTO;
import com.guilherme.helpdesk.repositories.ClienteRepository;
import com.guilherme.helpdesk.repositories.PessoaRepository;
import com.guilherme.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.guilherme.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		validaPorCpfEEmail(clienteDTO);
		Cliente cliente = new Cliente(clienteDTO);
		return clienteRepository.save(cliente);
	}

	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		clienteDTO.setId(id);
		Cliente cliente = findById(id);

		if (!clienteDTO.getSenha().equals(cliente.getSenha()))
			clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));

		validaPorCpfEEmail(clienteDTO);
		cliente = new Cliente(clienteDTO);
		return clienteRepository.save(cliente);
	}

	public void delete(Integer id) {
		Cliente cliente = findById(id);

		if (cliente.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O Cliente possui ordens de serviço e não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}

	private void validaPorCpfEEmail(ClienteDTO clienteDTO) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
		if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		pessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());

		if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
}
