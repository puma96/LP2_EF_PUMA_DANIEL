package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CategoriaEntity;

public interface CategoriaService {

	List<CategoriaEntity> findAllCategorias();

	CategoriaEntity findCategoriaById(Integer id);

	CategoriaEntity saveCategoria(CategoriaEntity categoria);

	void deleteCategoria(Integer id);
}
