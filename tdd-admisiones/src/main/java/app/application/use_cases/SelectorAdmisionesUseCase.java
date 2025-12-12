package app.application.use_cases;

import app.domain.repositories.IConvocatoriaRepository;

public class SelectorAdmisionesUseCase {
	
	// Clase a implementar con TDD
	
	private IConvocatoriaRepository convocatoria;
	
	
	
	public void seleccionar(long idConvocatoria) {
		convocatoria.obtenerInscripciones(idConvocatoria);
	}
	
	
}




