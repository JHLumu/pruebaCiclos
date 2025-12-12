package app.application.use_cases;

import app.domain.repositories.IConvocatoriaRepository;
import app.domain.services.ISelectorAdmisionesDomainService;
import app.domain.services.OrdenadorInscripcionesDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import app.domain.model.IConvocatoria;
import app.domain.model.IInscripcion;
import app.domain.model.Usuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
    
    // Mock: Suplantamos el servicio de dominio encargado de la selección (corte)
    @Mock
    ISelectorAdmisionesDomainService selectorAdmisionesService;
    

    @Test
    void test_GivenIdConvocatoria_WhenSeleccionar_ThenSolicitaInscripcionesAlRepositorio() {
    	long idConvocatoria = 1L;
        

        List<IInscripcion> listaConAlguien = List.of(mock(IInscripcion.class));
        when(selectorAdmisionesService.seleccionar(any(), anyDouble(), anyInt()))
            .thenReturn(listaConAlguien);
            
        IConvocatoria conv = mock(IConvocatoria.class);
        when(convocatoriaRepository.obtenerConvocatoria(idConvocatoria)).thenReturn(conv);

        selectorUseCase.seleccionar(idConvocatoria);


        verify(convocatoriaRepository).obtenerInscripciones(idConvocatoria);
    }
    
    @Test
    void test_GivenInscripcionesDelRepo_WhenSeleccionar_ThenSolicitaOrdenacionAlServicioDeDominio() {
    	long idConvocatoria = 1L;
        List<IInscripcion> inscripcionesSimuladas = new ArrayList<>();
        
        IConvocatoria conv = mock(IConvocatoria.class);
        when(convocatoriaRepository.obtenerConvocatoria(idConvocatoria)).thenReturn(conv);
        
        List<IInscripcion> listaConAlguien = List.of(mock(IInscripcion.class));
        when(selectorAdmisionesService.seleccionar(any(), anyDouble(), anyInt()))
            .thenReturn(listaConAlguien);

        when(convocatoriaRepository.obtenerInscripciones(idConvocatoria)).thenReturn(inscripcionesSimuladas);
        
        selectorUseCase.seleccionar(idConvocatoria);

        verify(ordenadorService).ordenar(inscripcionesSimuladas);
    }
    
    @Test
    void test_GivenInscripcionesOrdenadas_WhenSeleccionar_ThenLlamaAlServicioDeSeleccionConParametrosDeConvocatoria() {
        long idConvocatoria = 1L;
        int maxPlazas = 20;
        double precio = 150.50;

        IConvocatoria convocatoriaStub = mock(IConvocatoria.class);
        when(convocatoriaStub.getMaxPlazas()).thenReturn(maxPlazas);
        when(convocatoriaStub.getPrecio()).thenReturn(precio);

        List<IInscripcion> inscripciones = new ArrayList<>();
        List<IInscripcion> inscripcionesOrdenadas = new ArrayList<>();

        when(convocatoriaRepository.obtenerInscripciones(idConvocatoria)).thenReturn(inscripciones);
        when(convocatoriaRepository.obtenerConvocatoria(idConvocatoria)).thenReturn(convocatoriaStub);
        when(ordenadorService.ordenar(inscripciones)).thenReturn(inscripcionesOrdenadas);
        
        List<IInscripcion> listaConAlguien = List.of(mock(IInscripcion.class));
        when(selectorAdmisionesService.seleccionar(inscripcionesOrdenadas, precio, maxPlazas))
            .thenReturn(listaConAlguien);

        selectorUseCase.seleccionar(idConvocatoria);

        verify(selectorAdmisionesService).seleccionar(inscripcionesOrdenadas, precio, maxPlazas);
    }
    
    @Test
    void test_GivenInscripcionesSeleccionadas_WhenSeleccionar_ThenActualizaCreditoYCursosDeUsuariosAdmitidos() {
        long idConvocatoria = 1L;
        double precio = 200.0;
        int maxPlazas = 5;

        IConvocatoria convocatoriaStub = mock(IConvocatoria.class);
        when(convocatoriaStub.getPrecio()).thenReturn(precio);
        when(convocatoriaStub.getMaxPlazas()).thenReturn(maxPlazas);

        Usuario usuarioMock = mock(Usuario.class);
        
        IInscripcion inscripcionAdmitida = mock(IInscripcion.class);
        when(inscripcionAdmitida.getUser()).thenReturn(usuarioMock);
        
        List<IInscripcion> listaAdmitidos = List.of(inscripcionAdmitida);

        when(convocatoriaRepository.obtenerConvocatoria(idConvocatoria)).thenReturn(convocatoriaStub);
        when(selectorAdmisionesService.seleccionar(any(), eq(precio), eq(maxPlazas)))
            .thenReturn(listaAdmitidos);

        selectorUseCase.seleccionar(idConvocatoria);


        // Verificamos que se actualiza el estado del usuario con el precio correcto
        verify(usuarioMock).descontarCredito(precio);
        verify(usuarioMock).incrementarCursos();
    }
    
    @Test
    void test_GivenNingunUsuarioAdmisible_WhenSeleccionar_ThenLanzaExcepcion() {
        long idConvocatoria = 1L;
        double precio = 200.0;
        int maxPlazas = 5;

        IConvocatoria convocatoriaStub = mock(IConvocatoria.class);
        when(convocatoriaStub.getPrecio()).thenReturn(precio);
        when(convocatoriaStub.getMaxPlazas()).thenReturn(maxPlazas);

        when(convocatoriaRepository.obtenerConvocatoria(idConvocatoria)).thenReturn(convocatoriaStub);
        when(selectorAdmisionesService.seleccionar(any(), eq(precio), eq(maxPlazas)))
            .thenReturn(new ArrayList<>()); 

        // Verificamos que al ejecutar el método se lance una RuntimeException
        assertThrows(RuntimeException.class, () -> {
            selectorUseCase.seleccionar(idConvocatoria);
        });
    }
    
  
}