package com.formandera.application.usecases;

import com.formandera.domain.model.Curso;
import com.formandera.domain.model.PerfilUsuario;
import com.formandera.domain.interfaces.RepositorioCurso;
import com.formandera.application.dto.ResultadoCurso;
import com.formandera.application.ports.inbound.PortBusquedaCurso;

import java.util.List;
import java.util.stream.Collectors;

public class EncuentraCursosRecomendadosUseCase implements PortBusquedaCurso {
	
	private static final int SENIOR_MIN_AGE = 56;
	private static final int ES_FORMADO = 1;
	private static final int LIMIT_MIN_CURSOS = 10;
	
	private static final double DESC_MENOR_FORM = 0.20;
	private static final double DESC_SENIOR_FORM = 0.30;
	private static final double DES_ADICIONAL = 0.05;
	
	private final RepositorioCurso repositorio;
	
	public EncuentraCursosRecomendadosUseCase(RepositorioCurso repositorio) {
		this.repositorio = repositorio;
	}
	
	@Override
	public List<ResultadoCurso> findRecommendedCourses(PerfilUsuario usuario){
		List<Curso> cursos = repositorio.findByTematica(usuario.getTematica());
		
		return cursos.stream()
				.filter(c->c.getEdadRecomendada()<=usuario.getEdad())
				.filter(c->c.getValoracionMedia()>=usuario.getPuntuacionMinima())
				.map(c->{
					double precioFinal = aplicarDescuentos(usuario, c.getPrecioBase());
					return new ResultadoCurso(c.getNombre(), c.getValoracionMedia(), c.getPrecioBase(), precioFinal);
				})
				.collect(Collectors.toList());
	}
	
    private double aplicarDescuentos(PerfilUsuario u, double precioBase) {
        double factor = 1.0;

        if (esMenorConDescuento(u)) {
            factor -= DESC_MENOR_FORM;
        }
        if (esSenior(u) && esFormado(u)) {
            factor -= DESC_SENIOR_FORM ; 
        }
        if (pasaLimite(u)) {
            factor *= (1-DES_ADICIONAL); // -5% adicional
        }

        return round2(precioBase * factor);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
    
    private boolean esMenorConDescuento(PerfilUsuario u) {
    	return (u.getEdad() >= 12 && u.getEdad() < 18 && u.getCursosCompletados() >= 1 && u.getCursosCompletados() <= 5);
    }
    
    private boolean esSenior(PerfilUsuario u) {
    	return (u.getEdad() > SENIOR_MIN_AGE);
    }
    
    private boolean esFormado(PerfilUsuario u) {
    	return u.getCursosCompletados() > ES_FORMADO;
    }
    
    private boolean pasaLimite(PerfilUsuario u) {
    	return u.getCursosCompletados()>= (LIMIT_MIN_CURSOS - 1);
    }
}
