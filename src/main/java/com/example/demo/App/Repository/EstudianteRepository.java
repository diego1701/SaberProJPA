package com.example.demo.App.Repository;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.App.Entity.Estudiantes;

public interface EstudianteRepository extends CrudRepository <Estudiantes, Long> {
	Estudiantes findByestid(Long Estid);
}
