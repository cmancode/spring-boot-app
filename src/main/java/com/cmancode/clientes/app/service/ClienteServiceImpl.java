package com.cmancode.clientes.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmancode.clientes.app.dao.IClienteDao;
import com.cmancode.clientes.app.model.Cliente;

@Service("clienteService")
@Transactional
public class ClienteServiceImpl implements IClienteService{
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void saveClient(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteClient(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional
	public Page<Cliente> findAll(Pageable pageable) {
		Page<Cliente> clientePageable = null;
		clientePageable = clienteDao.findAll(pageable);
		return clientePageable;
	}

}
