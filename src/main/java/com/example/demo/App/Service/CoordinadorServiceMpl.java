package com.example.demo.App.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.App.Repository.CoordinadorRepository;
import com.example.demo.App.Entity.Coordinador;

@Service
public class CoordinadorServiceMpl implements CoordinadorService{

	@Autowired
	private CoordinadorRepository coordinadorService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Coordinador> findAll() {
		

		return (List<Coordinador>) coordinadorService.findAll();
	}

	@Override
	@Transactional
	public void save(Coordinador coordinador) {
		coordinadorService.save(coordinador);

		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		coordinadorService.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Coordinador findOne(Long id) {
		
		
		return coordinadorService.findById(id).orElse(null);
	}

	@Override
    public Coordinador findBycoorid(Long Coorid) {
        return coordinadorService.findBycoorid(Coorid);
    }
	
}
