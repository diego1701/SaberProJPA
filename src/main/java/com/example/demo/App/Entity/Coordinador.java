package com.example.demo.App.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="coordinador")
public class Coordinador {

	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		
		@Column(name="Coorid")
		private Long coorid; 
		
		@NotEmpty
		@Column(name="password")
		private String Password;
		
		@NotEmpty
		@Column(name="nombre")
		private String Nombre;
		
		@NotEmpty
		@Column(name="apellido")
		private String Apellido;
		
		@NotEmpty
		@Column(name="rol")
		private String Rol;

		public Coordinador() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getCoorid() {
			return coorid;
		}

		public void setCoorid(Long coordid) {
			coorid = coordid;
		}

		public String getPassword() {
			return Password;
		}

		public void setPassword(String password) {
			Password = password;
		}

		public String getNombre() {
			return Nombre;
		}

		public void setNombre(String nombre) {
			Nombre = nombre;
		}

		public String getApellido() {
			return Apellido;
		}

		public void setApellido(String apellido) {
			Apellido = apellido;
		}

		public String getRol() {
			return Rol;
		}

		public void setRol(String rol) {
			Rol = rol;
		}
		
		
		
}