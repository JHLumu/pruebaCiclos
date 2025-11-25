package com.formandera.adapters.outbound.impl;

import com.formandera.domain.interfaces.RepositorioCurso;
import com.formandera.domain.model.Curso;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepositorioCursoAdaptadorEnMemoria implements RepositorioCurso {
	
	private final List<Curso> cursos = List.of(
	        new Curso("Java Básico", "Programación", 12, 4.5, 120),
	        new Curso("Python Intermedio", "Programación", 16, 4.2, 150),
	        new Curso("Excel para principiantes", "Ofimática", 14, 4.7, 90),
	        new Curso("Fotografía Digital", "Arte", 18, 4.8, 200),
	        new Curso("Historia del Arte", "Arte", 10, 4.0, 100),
	        new Curso("Programación avanzada", "Programación", 20, 4.9, 220),
	        new Curso("Ofimática esencial", "Ofimática", 12, 3.9, 70),
	        new Curso("Arte Contemporáneo", "Arte", 16, 4.3, 110)
	    );
	
	@Override
    public List<Curso> findByTematica(String tematica) {
        return cursos.stream()
                .filter(c -> c.getTematica().equalsIgnoreCase(tematica))
                .collect(Collectors.toList());
    }
}
