package app.application.use_cases;

import java.util.List;

import app.domain.model.IInscripcion;
import app.domain.repositories.IConvocatoriaRepository;
import app.domain.services.OrdenadorInscripcionesDomainService;

// Clase a implementar con TDD
public class SelectorAdmisionesUseCase {
	
	// Atributos de la clase
	private IConvocatoriaRepository convocatoria;	
	private OrdenadorInscripcionesDomainService ordenadorService;
	
	// Métodos de la clase
	public void seleccionar(long idConvocatoria) {
		List<IInscripcion> inscripciones = convocatoria.obtenerInscripciones(idConvocatoria);
		ordenadorService.ordenar(inscripciones);
	}
	
	
}




