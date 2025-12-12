package app.application.use_cases;

import app.domain.repositories.IConvocatoriaRepository;
import app.domain.services.OrdenadorInscripcionesDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import app.domain.model.IInscripcion;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class SelectorAdmisionesUseCaseTest {

    // Mock: Suplantamos la infraestructura (Repositorio)
    @Mock
    IConvocatoriaRepository convocatoriaRepository;

    // Mock: Suplantamos el servicio de dominio (Ordenación)
    @Mock
    OrdenadorInscripcionesDomainService ordenadorService;

    // CUT: Clase Bajo Prueba con inyección automática
    @InjectMocks
    SelectorAdmisionesUseCase selectorUseCase;

    @Test
    void test_GivenIdConvocatoria_WhenSeleccionar_ThenSolicitaInscripcionesAlRepositorio() {
        long idConvocatoria = 1L;

        selectorUseCase.seleccionar(idConvocatoria);

        // Verificamos la interacción: ¿Se llamó al repositorio con el ID correcto?
        verify(convocatoriaRepository).obtenerInscripciones(idConvocatoria);
    }
    
    @Test
    void test_GivenInscripcionesDelRepo_WhenSeleccionar_ThenSolicitaOrdenacionAlServicioDeDominio() {
        long idConvocatoria = 1L;
        List<IInscripcion> inscripcionesSimuladas = new ArrayList<>();
        
        when(convocatoriaRepository.obtenerInscripciones(idConvocatoria)).thenReturn(inscripcionesSimuladas);
        selectorUseCase.seleccionar(idConvocatoria);

        verify(ordenadorService).ordenar(inscripcionesSimuladas);
    }
    
  
}