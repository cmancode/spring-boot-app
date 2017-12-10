package com.cmancode.clientes.app.dao;

import java.util.List;

import com.cmancode.clientes.app.model.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();

}
