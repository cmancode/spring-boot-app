package com.cmancode.clientes.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmancode.clientes.app.model.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public void saveClient(Cliente cliente);
	public Optional<Cliente> findById(Long id);
	public void deleteClient(Long id);
	
}
