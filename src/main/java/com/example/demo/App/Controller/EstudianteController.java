package com.example.demo.App.Controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.App.Entity.Estudiantes;
import com.example.demo.App.Service.EstudianteService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("estudiante")
public class EstudianteController {
	
	@Autowired
	private EstudianteService estudianteService;
	

	
	@RequestMapping(value="/listare",method = RequestMethod.GET)
	public String llamarListar(Model model) {
	List<Estudiantes> estudiante = estudianteService.findAll();	
	model.addAttribute("estudiante", estudiante);
	
	return "listar-coor";
}
	
	

	
	@RequestMapping(value={"/formel"},method = RequestMethod.GET)
	public String llamarformulario(Map<String,Object> model)
	{
		Estudiantes estudiante = new Estudiantes();
		

		model.put("estudiante", estudiante);
		return "form-addest";
	}
	
	@RequestMapping(value={"/forme"},method = RequestMethod.POST)
	public String guardar(@Valid Estudiantes estudiante,BindingResult result, Model model) {
		if (result.hasErrors()) {
	        for (FieldError error : result.getFieldErrors()) {
	            System.out.println("Error en campo " + error.getField() + ": " + error.getDefaultMessage());
	        }
	        return "form-addest";
	    }

		estudianteService.save(estudiante);
		return "redirect:listar-est";
	}



	@RequestMapping(value={"/eliminare/{id}"})
	public String eliminar (@PathVariable(value="id") Long id)
	{
		if (id > 0) {
			estudianteService.delete(id);
		}
		return "redirect:/listar-coor";
	}

	
	
	@RequestMapping(value={"/forme/{id}"})
	public String editar (@PathVariable(value="id") Long id, Map<String,Object> model)
	{
		Estudiantes estudiante=null;
		if (id > 0) {
			estudiante = estudianteService.findOne(id);
			
			} else {
				return "redirect:listar-est";
			}
		

		    model.put("estudiante", estudiante);
			return "login-Est";
		}
	
	
	@GetMapping(value = "/vere/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Estudiantes estudiante = estudianteService.findOne(id);
		if (estudiante == null) {
			
			return "redirect:/listar-est";
		}

		model.put("estudiante", estudiante);
		model.put("titulo", "Informe estudiante: " + estudiante.getNombre());
		return "ver-est";
	}
		
	
	
	
	@GetMapping(value = "/veree/{estid}")
	public String vere(@PathVariable(value = "estid") Long estid, Map<String, Object> model, RedirectAttributes flash) {
	    Estudiantes estudiante = estudianteService.findByestid(estid);
	    
	    if (estudiante == null) {
	        return "redirect:/listar-est";
	    }
	    
	    
	   
	    
	    Long puntajeCiencias = estudiante.getPuntaje_ciencias();
	    Long puntajeIngles = estudiante.getPuntaje_ingles();
	    Long puntajeDiseno = estudiante.getPuntaje_dis();
	    Long puntajeCompetencias = estudiante.getPuntaje_competencias();
	    Long puntajeComunicacion = estudiante.getPuntaje_comunicacion();
	    Long puntajeLectura = estudiante.getPuntaje_lectura();
	    Long puntajeProyectos = estudiante.getPuntaje_proyectos();
	    Long puntajeRaz = estudiante.getPuntaje_razonamiento();
	    Long niveingles = estudiante.getNivel_ingles();
	    
	    String levelingles = nivelIngles(niveingles);
	    String nivelCiencias = asignarNivel(puntajeCiencias);
	    String nivelIngles = asignarNivel(puntajeIngles);
	    String nivelDiseno = asignarNivel(puntajeDiseno);
	    String nivelCompetencias = asignarNivel(puntajeCompetencias);
	    String nivelComunicacion = asignarNivel(puntajeComunicacion);
	    String nivelLectura = asignarNivel(puntajeLectura);
	    String nivelProyectos = asignarNivel(puntajeProyectos);
	    String nivelRaz = asignarNivel(puntajeRaz);
	    
	    
	    Long promedio = calcularPromedio(
			    puntajeCiencias, puntajeIngles, puntajeDiseno,
			    puntajeCompetencias, puntajeComunicacion, puntajeLectura
			);
	    
	    
	    String nivelPromedio = asignarNivel(promedio);
	    
	    model.put("promedioPuntajes", promedio);
	    model.put("nivelPromedio", nivelPromedio);
	    model.put("estudiante", estudiante);
	    model.put("nivelCiencias", nivelCiencias);
	    model.put("nivelIngles", nivelIngles);
	    model.put("nivelDiseno", nivelDiseno);
	    model.put("nivelCompetencias", nivelCompetencias);
	    model.put("nivelComunicacion", nivelComunicacion );
	    model.put("nivelLectura", nivelLectura);
	    model.put("nivelProyectos", nivelProyectos);
	    model.put("nivelRaz", nivelRaz);
	    model.put("nivelingles", levelingles);
	    
	    model.put("titulo", "Informe estudiantes: " + estudiante.getNombre());

	    return "verest";
	}

	
	
	
	private String asignarNivel(Long puntaje) {
	    if (puntaje > 1 && puntaje <= 120) {
	        return "Nivel 1";
	    } else if (puntaje > 120 && puntaje <=150) {
	        return "Nivel 2";
	    }else if(puntaje >150){
	    	return "Nivel 3";
	    } else {
	        return "Nivel no definido";
	    }
	}
	
	
	private String nivelIngles(Long level) {
	    if (level == 1) {
	        return "Nivel A - Bajo";
	    } else if (level == 2 ) {
	        return "Nivel B - Medio";
	    }else if(level == 3){
	    	return "Nivel C - Alto";
	    } else {
	        return "Nivel no definido";
	    }
	}
	
	

	public Long calcularPromedio(Long... puntajes) {
	    if (puntajes.length == 0) {
	        return null;
	    }
	    
	    long suma = 0;
	    for (Long puntaje : puntajes) {
	        suma += puntaje;
	    }
	    
	    return (Long) suma / puntajes.length;
	}

	

	
	
	
	
	

	@GetMapping("/loginest")
    public String loginForm() {
        return "login-Est"; // Debe haber una plantilla HTML llamada "login.html" para mostrar el formulario de inicio de sesión.
    }
	
	@GetMapping("/listar-est")
    public String listarest() {
        return "listar-est"; // Debe haber una plantilla HTML llamada "login.html" para mostrar el formulario de inicio de sesión.
    }

	@PostMapping("/loginE")
	public String loginSubmit(@RequestParam Long estid, @RequestParam String password, Model model, HttpSession session) {
	    Estudiantes estudiante = estudianteService.findByestid(estid);

	    if (estudiante != null && estudiante.getPassword().equals(password)) {
	        session.setAttribute("estudiante", estudiante);
	        return "redirect:/veree/"+estid;
	    } else {
	        model.addAttribute("error", "Credenciales inválidas!");
	        return "login-Est";
	    }
	}



}

