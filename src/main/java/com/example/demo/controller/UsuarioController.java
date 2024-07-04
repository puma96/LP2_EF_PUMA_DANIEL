package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/registrar")
    public String showRegistrarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "registrar_usuario";
    }
    
    @PostMapping("/registrar")
    public String registrar(UsuarioEntity usuarioEntity, Model model) {
        usuarioService.crearUsuario(usuarioEntity, model);
        return "registrar_usuario";
    }
	
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(UsuarioEntity usuarioEntity, Model model, HttpSession session) {
        boolean usuarioValido = usuarioService.validarUsuario(usuarioEntity, session);
        if (usuarioValido) {
            
            String correoUsuario = (String) session.getAttribute("usuario");
            model.addAttribute("correo_usuario", correoUsuario);
            return "redirect:/menu";
        }
        model.addAttribute("loginInvalido", "No existe el usuario");
        model.addAttribute("usuario", new UsuarioEntity());
        return "login";
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}