package com.example.demo.controller;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/categorias/agregar")
	public String mostrarFormularioRegistroCategoria(Model model, HttpSession session) {
		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("nombre_usuario", usuario.getNombre());

		CategoriaEntity categoria = new CategoriaEntity();
		model.addAttribute("categoria", categoria);

		return "registrar_categoria";
	}

	@PostMapping("/categorias/registrar")
	public String registrarCategoria(@ModelAttribute("categoria") @Valid CategoriaEntity categoria,
			BindingResult result) {
		if (result.hasErrors()) {
			return "registrar_categoria";
		}

		categoriaService.saveCategoria(categoria);

		return "redirect:/menu";
	}
}
