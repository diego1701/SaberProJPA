package com.example.demo.App.Repository;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.App.Entity.Coordinador;


public interface CoordinadorRepository extends CrudRepository<Coordinador, Long> {
	Coordinador findBycoorid(Long Coorid);
	
}







