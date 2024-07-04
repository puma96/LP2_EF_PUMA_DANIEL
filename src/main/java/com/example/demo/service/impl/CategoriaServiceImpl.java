package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<CategoriaEntity> findAllCategorias() {
		return categoriaRepository.findAll();
	}

	@Override
	public CategoriaEntity findCategoriaById(Integer id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	@Override
	public CategoriaEntity saveCategoria(CategoriaEntity categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public void deleteCategoria(Integer id) {
		categoriaRepository.deleteById(id);

	}

}