package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;

public class ProductoServiceImpl  implements ProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<ProductoEntity> findAllProductos() {
		 return productoRepository.findAll();
	}

	@Override
	public ProductoEntity findProductoById(Integer id) {
		 return productoRepository.findById(id).orElse(null);
	}

	@Override
	public ProductoEntity saveProducto(ProductoEntity producto) {
		return productoRepository.save(producto);
	}

	@Override
	public void deleteProducto(Integer id) {
		productoRepository.deleteById(id);
		
	}

}
