package com.example.demo.App.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.App.Entity.Coordinador;
import com.example.demo.App.Entity.Estudiantes;
import com.example.demo.App.Service.CoordinadorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.example.demo.App.Service.EstudianteService;

	@Controller
	@SessionAttributes("coordinador")
	public class CoordinadorController {
		
		@Autowired
		private CoordinadorService coordinadorService;
		@Autowired
		private EstudianteService estudianteService;
		
		
		 @GetMapping("/coordinador")
		    public String coordinador() {
		        return "coordinador";
		    }
		
		@RequestMapping(value="/listar",method = RequestMethod.GET)
		public String llamarListar(Model model) {
			
		model.addAttribute("estudiante", estudianteService.findAll());
		return "listar-coor";
	}
		
	
	
	
		@RequestMapping(value={"/eliminarc/{id}"})
		public String eliminar (@PathVariable(value="id") Long id)
		{
			if (id > 0) {
				estudianteService.delete(id);
			}
			return "redirect:/listar-estudiantes";
		}
	
		
		@GetMapping(value = "/verc/{id}")
		public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		    Estudiantes estudiante = estudianteService.findOne(id);
		    
		    if (estudiante == null) {
		        return "redirect:/listar-estudiantes";
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

		    String nivelCiencias = asignarNivel(puntajeCiencias);
		    String nivelIngles = asignarNivel(puntajeIngles);
		    String nivelDiseno = asignarNivel(puntajeDiseno);
		    String nivelCompetencias = asignarNivel(puntajeCompetencias);
		    String nivelComunicacion = asignarNivel(puntajeComunicacion);
		    String nivelLectura = asignarNivel(puntajeLectura);
		    String nivelProyectos = asignarNivel(puntajeProyectos);
		    String nivelRaz = asignarNivel(puntajeRaz);
		    String levelingles = nivelIngles(niveingles);
		    
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

		    return "verestc";
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

		

	
		
		
		
		
		
	
		@GetMapping("/login")
	    public String loginForm() {
	        return "login-coor"; // Debe haber una plantilla HTML llamada "login.html" para mostrar el formulario de inicio de sesión.
	    }
		
		@GetMapping("/listar-coor")
	    public String listarcoor() {
	        return "listar-coor"; // Debe haber una plantilla HTML llamada "login.html" para mostrar el formulario de inicio de sesión.
	    }
		
		
		@GetMapping("/listar-estudiantes")
		public String listarEstudiantes(Model model) {
		    List<Estudiantes> estudiantes = estudianteService.findAll();
		    model.addAttribute("estudiantes", estudiantes);

		    Map<Long, Long> notasPromedioMap = new HashMap<>();
		    Map<Long, String> nivelp = new HashMap<>();
		    for (Estudiantes estudiante : estudiantes) {
		        Long notaPromedio = calcularPromedio(
		            estudiante.getPuntaje_ciencias() != null ? estudiante.getPuntaje_ciencias() : 1L,
		            estudiante.getPuntaje_ingles() != null ? estudiante.getPuntaje_ingles() : 1L,
		            estudiante.getPuntaje_dis() != null ? estudiante.getPuntaje_dis() : 1L,
		            estudiante.getPuntaje_competencias() != null ? estudiante.getPuntaje_competencias() : 1L,
		            estudiante.getPuntaje_comunicacion() != null ? estudiante.getPuntaje_comunicacion() : 1L,
		            estudiante.getPuntaje_lectura() != null ? estudiante.getPuntaje_lectura() : 1L,
		            estudiante.getPuntaje_proyectos() != null ? estudiante.getPuntaje_proyectos() : 1L,
		            estudiante.getPuntaje_razonamiento() != null ? estudiante.getPuntaje_razonamiento() : 1L
		        );
		        Long notapfinal = notaPromedio - 1;
		        String nivelPromedio = asignarNivel(notapfinal);
		        notasPromedioMap.put(estudiante.getId(), notapfinal);
		        nivelp.put(estudiante.getId(), nivelPromedio);
		    }

		    model.addAttribute("notasPromedioMap", notasPromedioMap);
		    model.addAttribute("nivelp", nivelp);

		    return "listar-coor";
		}


		
	
		
		@RequestMapping(value={"/formc/{id}"})
		public String editar (@PathVariable(value="id") Long id, Map<String,Object> model)
		{
			Estudiantes estudiante=null;
			if (id > 0) {
				estudiante = estudianteService.findOne(id);
				
				} else {
					return "redirect:/listar-estudiantes";
				}
			

			    model.put("estudiante", estudiante);
				return "form-addest";
			}
		
		
		
		

		@RequestMapping(value={"/formcl"},method = RequestMethod.GET)
		public String llamarformularioc(Map<String,Object> model)
		{
			Estudiantes estudiante = new Estudiantes();
			

			model.put("estudiante", estudiante);
			return "form-addest";
		}
		
		@RequestMapping(value={"/formc"},method = RequestMethod.POST)
		public String guardar(@Valid Estudiantes estudiante,BindingResult result, Model model) {
			if (result.hasErrors()) {
		        for (FieldError error : result.getFieldErrors()) {
		            System.out.println("Error en campo " + error.getField() + ": " + error.getDefaultMessage());
		        }
		        return "form-addest";
		    }

			estudianteService.save(estudiante);
			return "redirect:listar-estudiantes";
		}


			

	    @PostMapping("/login")
	    public String loginSubmit(@RequestParam Long Coorid, @RequestParam String password, Model model, HttpSession session) {
	        Coordinador coordinador = coordinadorService.findBycoorid(Coorid);

	        if (coordinador != null && coordinador.getPassword().equals(password)) {
	            session.setAttribute("coordinador", coordinador);        	
	            return "redirect:/listar-estudiantes";
	        } else {
	            model.addAttribute("error", "Credenciales inválidas");
	            return "login-coor";
	        }
	    }
	
	   

  
	
}
