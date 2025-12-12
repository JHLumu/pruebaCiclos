package app.application.use_cases;

import app.domain.repositories.IConvocatoriaRepository;
import app.domain.services.OrdenadorInscripcionesDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

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
    void debeSolicitarInscripcionesAlRepositorio() {
        long idConvocatoria = 1L;

        selectorUseCase.seleccionar(idConvocatoria);

        // Verificamos la interacción: ¿Se llamó al repositorio con el ID correcto?
        verify(convocatoriaRepository).obtenerInscripciones(idConvocatoria);
    }
}