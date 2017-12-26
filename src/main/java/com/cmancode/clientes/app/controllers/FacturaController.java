package com.cmancode.clientes.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmancode.clientes.app.model.Cliente;
import com.cmancode.clientes.app.model.Factura;
import com.cmancode.clientes.app.service.IClienteService;

@Controller
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	
	@RequestMapping(value = "/factura/{clienteId}", method = RequestMethod.GET)
	public String crearFactura(@PathVariable("clienteId") Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findById(id);
		if(cliente == null) {
			flash.addAttribute("info", "El cliente no existe en la base de datos");
			return "redirect: clientes";
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");
		
		return "factura/factura";
	}
	
}
