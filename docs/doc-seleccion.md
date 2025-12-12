# **Clase bajo prueba (CUT):** `SelectorAdmisionesUseCase`

## Roles

Jiahui  --->    Tester  
Jesús   --->    Dev

## Identificación de Dobles de Prueba (Mocks y Stubs)

Para aislar completamente la lógica del caso de uso, se han identificado las siguientes dependencias que deben ser suplantadas durante los tests:

| Dependencia | Tipo de Doble | Justificación |
| :--- | :--- | :--- |
| **`IConvocatoriaRepository`** | **MOCK** | Es una dependencia externa (infraestructura). Necesitamos confirmar que el caso de uso llama al método `obtenerInscripciones()` con el ID correcto, sin acceder a una base de datos real. |
| **`OrdenadorInscripcionesDomainService`** | **MOCK** | Es un servicio de dominio ajeno a la unidad que estamos probando. No queremos testear el algoritmo de ordenación aquí (se prueba en su propia rama), sino verificar que el caso de uso *delega* la responsabilidad de ordenar a este servicio. |
| **`IInscripcion`** | **STUB** | Es un modelo de datos (interfaz). Solo necesitamos que devuelva datos predefinidos (`getCredito()`, `getCursos()`) para ejercitar la lógica de selección. Actúa como Stub (aunque técnicamente lo generemos con `Mockito.mock` por ser una interfaz). |
| **`IConvocatoria`** (si aplica) | **STUB** | Modelo de datos necesario para consultar límites (`getMaxPlazas()`). No verificamos interacción con ella, solo consultamos su estado. |

## Iteraciones de TDD

### Iteración 1

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`  

#### TEST1 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/8687b3f716ef937cc8e23c77e85dbd695ea87a78))  

Se crea el primer test para asegurar la colaboración con el repositorio.  

```java
@Test
void test_GivenIdConvocatoria_WhenSeleccionar_ThenSolicitaInscripcionesAlRepositorio() {
    long idConvocatoria = 1L;
    selectorUseCase.seleccionar(idConvocatoria);
    verify(convocatoriaRepository).obtenerInscripciones(idConvocatoria);
}
```

#### DEV1 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/a1dd09455236feda339ef2d5bab9cc0a5b1dfe47))

Se crea el atributo repositorio de la clase, Se crea la función `seleccionar()` y dentro de esta se llama a `obtenerInscripciones`.

```java
public class SelectorAdmisionesUseCase {
 
 // Clase a implementar con TDD
 
 private IConvocatoriaRepository convocatoria;
 
 
 
 public void seleccionar(long idConvocatoria) {

  convocatoria.obtenerInscripciones(idConvocatoria);
 }
 
 
}
```

-----

### Iteración 2

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`

#### TEST2 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/e71738240d1f1734a45aa4a3101f1a5dcd3dfb9d))

Se añade un nuevo test para verificar la interacción con el servicio de dominio.

```java
@Test
void test_GivenInscripcionesDelRepo_WhenSeleccionar_ThenSolicitaOrdenacionAlServicioDeDominio() {
    // ARRANGE
    long idConvocatoria = 1L;
    
    // 1. Creamos la lista simulada usando la interfaz IInscripcion
    List<IInscripcion> inscripcionesSimuladas = new ArrayList<>();
    
    // 2. Creamos un elemento falso (mock) de esa interfaz para rellenar la lista
    IInscripcion inscripcionFalsa = mock(IInscripcion.class);
    inscripcionesSimuladas.add(inscripcionFalsa);
    
    // 3. Programamos al mock del repositorio para que devuelva esta lista cuando le pregunten
    when(convocatoriaRepository.obtenerInscripciones(idConvocatoria)).thenReturn(inscripcionesSimuladas);

    // ACT
    selectorUseCase.seleccionar(idConvocatoria);

    // ASSERT
    // Verificamos que el caso de uso llamó al servicio de ordenación pasando esa misma lista
    verify(ordenadorService).ordenar(inscripcionesSimuladas);
}
```

#### DEV2 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/a1dd09455236feda339ef2d5bab9cc0a5b1dfe47))

Se amplia `seleccionar()` para guardar el resultado que devuelve `obtenerInscripciones()` y ordenarlos con `ordenar()` de la clase `OrdenadorInscripcionesDomainService`.

```java
// Atributos de la clase
 private IConvocatoriaRepository convocatoria; 
 private OrdenadorInscripcionesDomainService ordenadorService;
 
 // Métodos de la clase
 public void seleccionar(long idConvocatoria) {
  List<IInscripcion> inscripciones = convocatoria.obtenerInscripciones(idConvocatoria);
  ordenadorService.ordenar(inscripciones);
 }
```

-----

### Iteración 3

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`  

#### TEST3 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/9ac5d6d8c46d431717a181e85c6a5ce38e2b9d40))  

Se añade un nuevo test para verificar que, tras ordenar las inscripciones, el caso de uso recupera la información de la convocatoria y delega la lógica de selección al servicio de dominio `ISelectorAdmisionesDomainService`.

```java
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

        selectorUseCase.seleccionar(idConvocatoria);

        verify(selectorAdmisionesService).seleccionar(inscripcionesOrdenadas, precio, maxPlazas);
    }
```

#### DEV 3 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/2d1721cc1d3003e53b65f0fcf62e82989b1f4022))  

Se añade el atributo `selectorService` para la llamada a `seleccionar(inscripciones, precio, maxPlazas)`. Dado a que convocatoria es null para TEST1 y TEST2, se ha añadido un condicional que solo ejecuta el TEST3.

```java
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
   selectorService.seleccionar(ordenadas, convocatoria.getPrecio(), convocatoria.getMaxPlazas());
  }
  
 }
```

-----

### Iteración 4

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`  

#### TEST4 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/c74d27910fc772508e3cc5dd540fafa55ca9772b))  

Se añade un nuevo test para verificar que, una vez obtenidos los alumnos admitidos del servicio de dominio, el caso de uso se encarga de aplicar los efectos colaterales (descontar crédito e incrementar cursos) sobre los usuarios correspondientes.

```java
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
```

#### DEV4 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/2148dbbf8e6aa52cf9c6d7711e5f96298245c578))  

La lista de inscripciones que han sido admitidas por el método `seleccionar()` de `ISelectorAdmisionesDomainService` se recorre para descontar el crédito del usuario, además de incrementar el número de cursos del usuario asociado.

```java

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

```

-----

### Iteración 5

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`  

#### TEST5 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/b1355e95fc60482d5d62232bc57b40092bb6712a))  

Se añade un test para verificar que el caso de uso impide continuar si no se ha podido seleccionar a ningún candidato, lanzando una excepción.

```java
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