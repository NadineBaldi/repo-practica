package isi.died.parcial01.ejercicio02.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import Parte1.Capacitacion;
import isi.died.parcial01.ejercicio02.db.BaseDeDatos;
import isi.died.parcial01.ejercicio02.dominio.*;
import isi.died.parcial01.ejercicio02.dominio.Inscripcion.Estado;


public class MySysAcadImpl implements MySysAcad {
	private static final BaseDeDatos DB = new BaseDeDatos();


	private List<Materia> materia = new ArrayList<Materia>();
	
	@Override
	public void registrarMateria(Materia d) {
		this.materia.add(d);
	}
	
	private List<Docente> docentes = new ArrayList<Docente>();
	
	@Override
	public void registrarDocente(Docente d) {
		this.docentes.add(d);
	}
	
	private List<Alumno> alumnos = new ArrayList<Alumno>();
	
	@Override
	public void registrarAlumnos(Alumno d) {
		this.alumnos.add(d);
	}
	

	@Override
	public void inscribirAlumnoCursada(Docente d, Alumno a, Materia m, Integer cicloLectivo) {
		Inscripcion insc = new Inscripcion(cicloLectivo,Inscripcion.Estado.CURSANDO);
		d.agregarInscripcion(insc);
		a.addCursada(insc);
		m.addInscripcion(insc);
		// DESCOMENTAR Y gestionar excepcion
		//DB.guardar(insc);
	}

	@Override
	public void inscribirAlumnoExamen(Docente d, Alumno a, Materia m) {
		Examen e = new Examen();
		a.addExamen(e);
		d.agregarExamen(e);
		m.addExamen(e);
		// DESCOMENTAR Y gestionar excepcion
		// DB.guardar(e);
	}
	
	/*
	 * En la clase MySysAcadImpl implementar el metodo registrarNota para que:
	Setear la nota en el examen.
	Si la nota es 6 o superior,
	buscar el alumno y actualizar la última inscripción a esta materia como PROMOCIONADA.
	 */
	
	public void registrarNota(Examen e) {
		
			
			for (int i=0; i<alumnos.size(); i++) {
				
				if (alumnos.get(i).getNombre().equals(e.getAlumno().getNombre())) {
					
					alumnos.get(i).addExamen(e); //seteo el examen (que lleva una nota)
					
				}
				
				if (e.getNota() >= 6) {
					
					alumnos.get(i).getMateriasCursadas().get(i).setEstado(Estado.PROMOCIONADO);
				
				
			}
			
		}
		
	}
	
	
	/*
	 * Luego agregar a la interface MySysAcad el método "public Double promedioAprobados(Materia m);" 
	 * que calcula para una materia el promedio de todos los examenes, pero teniendo en cuenta 
	 * solo aquellos que fueron aprobados. En la implementación deberá usar expresiones lambdas.
	 */
	
	public Double promedioAprobados(Materia m) {
		
		OptionalDouble resultado = this.materia
					.stream()
					.map(e -> e.getExamenes())
					.map(e -> (Examen) e)
					.filter(e -> e.getNota() >= 6)
					.average();
		
		return resultado.orElse(0.0);
	}
	
	
	/*
	 * Agregar el metodo "List<Alumno> inscriptos(Materia,Integer ciclo)" 
	 * que retoran la lista de inscriptos anotados en una materia ordenada alfabeticamente.
	 */
	
	public List<Alumno> inscriptos(Materia m, Integer ciclo) {
		
		return alumnos.stream().sorted((a1, a2) -> a1.getNombre().compareTo(a2.getNombre())).collect(Collectors.toList());
	}
	
	

}
