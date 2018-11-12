package com.cmancode.clientes.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@GetMapping(value = {"/login"})
	public String login(Model model, Principal usuario, RedirectAttributes flash) {
		
		if(usuario != null) {
			flash.addFlashAttribute("info", "El usuario ya se encuentra logueado!");
			return "redirect:/clientes";
		}
		
		return "login";
	}
	
}