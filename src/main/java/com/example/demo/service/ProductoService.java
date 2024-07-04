package com.example.demo.service;

import com.example.demo.entity.ProductoEntity;
import java.util.List;

public interface ProductoService {

	List<ProductoEntity> findAllProductos();

	ProductoEntity findProductoById(Integer id);

	ProductoEntity saveProducto(ProductoEntity producto);

	void deleteProducto(Integer id);
}