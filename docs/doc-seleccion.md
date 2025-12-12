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

## Iteración 1

**Clase Test:** `SelectorAdmisionesUseCaseTest`  
**Clase Dev:** `SelectorAdmisionesUseCase`  

### TEST1 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/8687b3f716ef937cc8e23c77e85dbd695ea87a78))  

Se crea el primer test para asegurar la colaboración con el repositorio.  

```java
@Test
void test_GivenIdConvocatoria_WhenSeleccionar_ThenSolicitaInscripcionesAlRepositorio() {
    long idConvocatoria = 1L;
    selectorUseCase.seleccionar(idConvocatoria);
    verify(convocatoriaRepository).obtenerInscripciones(idConvocatoria);
}
```

### DEV1 ([Ver commit](https://github.com/asuliitoh/Calso2526_P6-grupo07/commit/a1dd09455236feda339ef2d5bab9cc0a5b1dfe47))

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