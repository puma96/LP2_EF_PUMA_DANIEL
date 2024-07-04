package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_categoria")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_id")
	private Integer categoria_id;

	@Column(name = "descripcion", nullable = false, length = 45)
	private String descripcion;
}