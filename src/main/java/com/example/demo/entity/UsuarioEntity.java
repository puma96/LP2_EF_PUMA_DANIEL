package com.example.demo.entity;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuario")
@Data
public class UsuarioEntity {

	@Id
	@Email(message = "El correo debe ser válido y contener al menos un '@'")
	@Column(nullable = false, unique = true)
	private String correo;

	@Column(nullable = false)
	private String password;

	@Pattern(regexp = "^[^0-9]*$", message = "El nombre no debe contener números")
	@Column(nullable = false)
	private String nombre;

	@Pattern(regexp = "^[^0-9]*$", message = "Los apellidos no deben contener números")
	@Column(nullable = false)
	private String apellidos;

	@Column(name = "fecha_nacimiento", nullable = false)
	private LocalDate fecnac;

	private String urlImagen;

}