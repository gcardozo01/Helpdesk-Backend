package com.guilherme.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guilherme.helpdesk.domain.Cliente;
import com.guilherme.helpdesk.domain.enums.Perfil;

public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotNull(message = "O campo NOME é requerido")
	protected String nome;
	
	@CPF
	@NotNull(message = "O campo CPF é requerido")
	protected String cpf;
	
	@NotNull(message = "O campo E-MAIL é requerido")
	protected String email;
	
	@NotNull(message = "O campo SENHA é requerido")
	protected String senha;
	
	@CollectionTable(name = "PERFIS")
	protected Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ClienteDTO() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.email = cliente.getEmail();
		this.senha = cliente.getSenha();
		this.perfis = cliente.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = cliente.getDataCriacao();
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
