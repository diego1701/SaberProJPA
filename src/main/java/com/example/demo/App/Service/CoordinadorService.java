package com.example.demo.App.Service;

import java.util.List;

import com.example.demo.App.Entity.Coordinador;

public interface CoordinadorService {


	public List<Coordinador> findAll();
	
	public void save(Coordinador coordinador);
	
	public void delete(Long id);
	
	public Coordinador findOne(Long id);
	
	Coordinador findBycoorid(Long Coorid);
	
	
	    
	


	
	
	
	
}
