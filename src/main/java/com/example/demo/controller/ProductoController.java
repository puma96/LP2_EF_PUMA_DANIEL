package com.example.demo.controller;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private PdfServiceImpl pdfService;

	@GetMapping("/menu")
	public String listarProductos(Model model, HttpSession session) {
		List<ProductoEntity> productos = productoService.findAllProductos();
		model.addAttribute("productos", productos);
		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("foto", usuario.getUrlImagen());
		model.addAttribute("nombre_usuario", usuario.getNombre());

		return "menu";
	}

	@GetMapping("/productos/agregar")
	public String mostrarFormularioRegistro(Model model, HttpSession session) {

		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("nombre_usuario", usuario.getNombre());

		ProductoEntity producto = new ProductoEntity();
		model.addAttribute("producto", producto);

		List<CategoriaEntity> categorias = categoriaService.findAllCategorias();
		model.addAttribute("categorias", categorias);

		return "registrar_producto";
	}

	@PostMapping("/productos/registrar")
	public String registrarProducto(@ModelAttribute("producto") @Valid ProductoEntity producto, BindingResult result) {
		if (result.hasErrors()) {
			return "registrar_producto";
		}

		CategoriaEntity categoria = categoriaService.findCategoriaById(producto.getCategoriaEntity().getCategoria_id());
		producto.setCategoriaEntity(categoria);

		productoService.saveProducto(producto);

		return "redirect:/menu";
	}

	@GetMapping("/productos/eliminar/{id}")
	public String eliminarProducto(@PathVariable("id") Integer id) {
		productoService.deleteProducto(id);
		return "redirect:/menu";
	}

	@GetMapping("/productos/editar/{id}")
	public String mostrarFormularioEdicion(HttpSession session, @PathVariable("id") Integer id, Model model) {
		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("nombre_usuario", usuario.getNombre());

		ProductoEntity producto = productoService.findProductoById(id);
		if (producto == null) {
			return "redirect:/menu";
		}
		model.addAttribute("producto", producto);

		List<CategoriaEntity> categorias = categoriaService.findAllCategorias();
		model.addAttribute("categorias", categorias);

		return "editar_producto";
	}

	@PostMapping("/productos/actualizar")
	public String actualizarProducto(@ModelAttribute("producto") @Valid ProductoEntity producto, BindingResult result) {
		if (result.hasErrors()) {
			return "editar_producto";
		}

		ProductoEntity productoExistente = productoService.findProductoById(producto.getIdProducto());
		if (productoExistente == null) {
			return "redirect:/menu";
		}

		productoExistente.setNombreProducto(producto.getNombreProducto());
		productoExistente.setPrecio(producto.getPrecio());
		productoExistente.setStock(producto.getStock());

		CategoriaEntity categoria = categoriaService.findCategoriaById(producto.getCategoriaEntity().getCategoria_id());
		productoExistente.setCategoriaEntity(categoria);

		productoService.saveProducto(productoExistente);

		return "redirect:/menu";
	}

	@GetMapping("/productos/detalles/{id}")
	public String verDetallesProducto(HttpSession session, @PathVariable("id") Integer id, Model model) {
		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("nombre_usuario", usuario.getNombre());

		ProductoEntity producto = productoService.findProductoById(id);
		if (producto == null) {
			return "redirect:/menu";
		}
		model.addAttribute("producto", producto);
		return "detalles_producto";
	}

	@PostMapping("/productos/buscar")
	public String buscarProductoPorId(HttpSession session,
			@RequestParam(value = "idProducto", required = false) Integer idProducto, Model model) {
		List<ProductoEntity> productos;
		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);
		model.addAttribute("nombre_usuario", usuario.getNombre());

		if (idProducto == null) {
			productos = productoService.findAllProductos();
		} else {
			ProductoEntity producto = productoService.findProductoById(idProducto);
			if (producto == null) {
				productos = productoService.findAllProductos();
				model.addAttribute("error", "No se encontró ningún producto con el ID proporcionado.");
			} else {
				productos = List.of(producto);
			}
		}
		model.addAttribute("foto", usuario.getUrlImagen());
		model.addAttribute("productos", productos);
		return "menu"; // Asegúrate de que esta sea la vista correcta.
	}

	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
		List<ProductoEntity> productos = productoService.findAllProductos();

		String correoUsuario = (String) session.getAttribute("usuario");
		UsuarioEntity usuario = usuarioService.buscarUsuarioPorCorreo(correoUsuario);

		Map<String, Object> datosPdf = new HashMap<>();
		datosPdf.put("productos", productos);
		datosPdf.put("usuario", usuario.getNombre());
		ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("template_pdf", datosPdf);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfBytes));
	}

}
