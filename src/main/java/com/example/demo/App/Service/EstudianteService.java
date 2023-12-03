package com.example.demo.App.Service;

import java.util.List;

import com.example.demo.App.Entity.Estudiantes;

public interface EstudianteService {

	public List<Estudiantes> findAll();
	
	public void save(Estudiantes estudiantes);
	
	public void delete(Long id);
	
	public Estudiantes findOne(Long id);
	
	Estudiantes findByestid(Long Estid);
	
}
