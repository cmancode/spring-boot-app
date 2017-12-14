package com.cmancode.clientes.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmancode.clientes.app.model.Cliente;
import com.cmancode.clientes.app.paginator.PageRender;
import com.cmancode.clientes.app.service.IClienteService;

@Controller
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	
	@RequestMapping(value = "/detalle/{id}", method = RequestMethod.GET)
	public String detalleCliente(@PathVariable("id") Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findById(id);

		if(cliente == null) {
			flash.addFlashAttribute("info", "El cliente no existe en la base de datos");
			return "redirect:clientes";
		}
		
		model.addAttribute("client", cliente);
		model.addAttribute("titulo", "Detalle de cliente");
		
		return "detalle";
	}
	
	
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<Cliente> clientesPageable = null;
		Pageable pageRequest = null;
		PageRender<Cliente> pageRender;
		pageRequest = PageRequest.of(page, 4);
		clientesPageable = clienteService.findAll(pageRequest);
		pageRender = new PageRender<>("/clientes", clientesPageable);
		model.addAttribute("titulo", "Lista de clientes");
		model.addAttribute("clientes", clientesPageable);
		model.addAttribute("page", pageRender);
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
	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public String saveClient(@Valid Cliente cliente, @RequestParam("file") MultipartFile file, BindingResult result, RedirectAttributes flash, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("encabezado", "Nuevo Cliente");
			model.addAttribute("boton", "Registrar cliente");
			return "nuevo-cliente";
		}
		
		if(!file.isEmpty()) {
			//Se establece donde se almacenarán los recursos
			Path directorioRecuros = Paths.get("src//main//resources//static/uploads");
			String rootPath = directorioRecuros.toFile().getAbsolutePath();
			try {
				byte[] bytes = file.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Imagen '"+ file.getOriginalFilename() + "' almacenada con éxito!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cliente.setFoto(file.getOriginalFilename());
		}
		
		flash.addFlashAttribute("success", "Información registrada con éxito!");
		clienteService.saveClient(cliente);
		return "redirect:/clientes";
	}
	
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public String findByIdClient(@PathVariable("id") Long id, Cliente cliente, Model model) {
		
		if(id > 0) {
			cliente = clienteService.findById(id);
		}else {
			return "redirect: clientes";
		}
		model.addAttribute("encabezado", "Actualizar Cliente");
		model.addAttribute("boton", "Actualzar");
		model.addAttribute("cliente", cliente);
		return "nuevo-cliente";
	}
	
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
	public String deleteClient (@PathVariable("id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			clienteService.deleteClient(id);
		}
		flash.addFlashAttribute("warning", "Datos eliminados con exitosamente!");
		return "redirect:/clientes";
	}
	
}
