package com.example.demo.App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.App.Repository.EstudianteRepository;
import com.example.demo.App.Entity.Estudiantes;


@Service
public class EstudianteServiceMpl implements EstudianteService{

	
	@Autowired
	private EstudianteRepository estudianteService;
	

	
	@Override
	@Transactional(readOnly = true)
	public List<Estudiantes> findAll() {
		

		return (List<Estudiantes>) estudianteService.findAll();
	}

	@Override
	@Transactional
	public void save(Estudiantes estudiantes) {
		estudianteService.save(estudiantes);

		
	}

	@Override
	@Transactional
	public void delete(Long id) {

		estudianteService.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiantes findOne(Long id) {
		
		
		return estudianteService.findById(id).orElse(null);
	}

	@Override
    public Estudiantes findByestid(Long Estid) {
        return estudianteService.findByestid(Estid);
    }

	
	
}
