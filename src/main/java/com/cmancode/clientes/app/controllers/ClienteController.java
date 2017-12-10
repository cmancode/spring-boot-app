package com.cmancode.clientes.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cmancode.clientes.app.dao.IClienteDao;
import com.cmancode.clientes.app.model.Cliente;

@Controller
public class ClienteController {

	@Autowired
	private IClienteDao clienteDao;
	
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public String listarClientes(Model model) {
		model.addAttribute("titulo", "Lista de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "clientes";
	}
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public String nuevoCliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Nuevo cliente");
		model.addAttribute("encabezado", "Nuevo Cliente");
		model.addAttribute("boton", "Registrar Cliente");
		return "nuevo-cliente";
	}
}
