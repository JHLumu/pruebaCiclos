package app.application.use_cases;

import java.util.List;

import app.domain.model.IConvocatoria;
import app.domain.model.IInscripcion;
import app.domain.model.Usuario;
import app.domain.repositories.IConvocatoriaRepository;
import app.domain.services.ISelectorAdmisionesDomainService;
import app.domain.services.OrdenadorInscripcionesDomainService;

// Clase a implementar con TDD
public class SelectorAdmisionesUseCase {
	
	// Atributos de la clase
	private IConvocatoriaRepository convocatoriaRepository;	
	private OrdenadorInscripcionesDomainService ordenadorService;
	private ISelectorAdmisionesDomainService selectorService;
	
	// Métodos de la clase
	public void seleccionar(long idConvocatoria) {
		List<IInscripcion> inscripciones = convocatoriaRepository.obtenerInscripciones(idConvocatoria);
		List<IInscripcion> ordenadas = ordenadorService.ordenar(inscripciones);
		IConvocatoria convocatoria = convocatoriaRepository.obtenerConvocatoria(idConvocatoria);
		if (convocatoria != null) {
			List<IInscripcion> admitidos = selectorService.seleccionar(ordenadas, convocatoria.getPrecio(), convocatoria.getMaxPlazas());
			if (admitidos != null) {
				
				for (IInscripcion admitido : admitidos) {
					Usuario user = admitido.getUser();
					user.descontarCredito(convocatoria.getPrecio());
					user.incrementarCursos();
				}
				
			}
		}
		
	}
		
	
}




