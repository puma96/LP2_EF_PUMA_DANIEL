package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producto_id")
	private Integer idProducto;

	@Column(name = "nombre_producto", nullable = false)
	private String nombreProducto;

	@Column(name = "precio", nullable = false)
	private Double precio;

	@Column(name = "stock", nullable = false)
	private Integer stock;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private CategoriaEntity categoriaEntity;

}