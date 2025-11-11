package com.formandera.domain.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.formandera.domain.models.Curso;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


class ListaCursosTest {
    
	private static final String TEMATICA_EJEMPLO = "Temática 1";
	
	private static final String NOMBRE_EJEMPLO = "Ejemplo 1";
	
    private ListaCursos listaCursos;
    
    @BeforeEach
    void setUp() {
        listaCursos = new ListaCursos();
    }

    
    /**
     * ***************************** PRUEBAS UNITARIAS DESTINADAS A PROBAR LOS CAMINOS BÁSICOS DE buscarCursosPorNivel() *****************************
     */
    
    @Test
    void test_given_listatematicas_empty_then_throw_IllegalStateException() {
    
    	// Se busca con la lista vacía.
        assertThrows(IllegalStateException.class, () -> listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2), "ERROR: buscarCursosPorNivel debe lanzar una excepción"
        		+ "IllegalStateException en caso de que listaTematicas esté vacía.");
        
    }

    @Test
    void test_given_listatematicas_not_contains_tematica_then_throw_IllegalArgumentException() {

    	// Se añade un curso con una temática. Posteriormente se busca con una temática distinta.
        listaCursos.addCurso(new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 3, 3));
        assertThrows(IllegalArgumentException.class, () -> listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 3),
        		"ERROR: buscarCursosPorNivel() debe lanzar IllegalArgumentException si la temática no se encuentra en listaTematicas.");
    }

    
    @Test
    void test_given_listatematicas_contains_tematica_with_empty_sublist_courses_then_return_empty_list() {
    
    	// Se añade una temática vacía, sin cursos. Se busca dicha temática posteriormente.
        listaCursos.addTematica(TEMATICA_EJEMPLO);
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2);
        assertTrue(resultado.isEmpty(),
            "ERROR: buscarCursosPorNivel() debe devolver una lista vacía si la sublista de cursos está vacía.");
    }
    
    @Test
    void test_given_listatematicas_contains_tematica_and_sublist_with_not_minimum_course_level() {
        
    	// Se añaden cursos con la misma temática pero con un nivel inferior al usado posteriormente para buscar.
        Curso curso1 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 1, 1);
        Curso curso2 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 2, 2);
        listaCursos.addCurso(curso1);
        listaCursos.addCurso(curso2);
        
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 3);
        
        assertTrue(resultado.isEmpty(), "ERROR: buscarCursosPorNivel() debe devolver una lista vacía si ningún curso supera el nivel mínimo.");
    }

   
    @Test
    void test_given_resultado_list_with_one_element_then_skip_bubble_sort_inner_loop() {
       
    	// Se crea un curso con la temática y nivel utilizados posteriormente para buscar.
        Curso curso = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 3, 3);
        listaCursos.addCurso(curso);
        
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 3);
        
        assertEquals(1, resultado.size(), "ERROR: buscarCursosPorNivel() debe devolver una lista con un solo elemento.");
        
        // Se comprueba si el curso devuelto es el mismo que el añadido.
        assertEquals(resultado.get(0), curso, "ERROR: buscarCursosPorNivel() debe devolver un curso con temática y nivel mínimo especificados y que haya sido agregado anteriormente.");
        
    }


 
    @Test
    void test_given_listatematica_contains_tematica_and_sublist_with_courses_with_same_valoracion() {
        
    	// Todos los cursos tienen la misma valoracion.
        Curso curso1 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 3, 3);
        Curso curso2 = new Curso(NOMBRE_EJEMPLO + "2", TEMATICA_EJEMPLO, 4, 3);
        Curso curso3 = new Curso(NOMBRE_EJEMPLO + "3", TEMATICA_EJEMPLO, 5, 3);
        listaCursos.addCurso(curso1);
        listaCursos.addCurso(curso2);
        listaCursos.addCurso(curso3);
        
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2);
        
 
        assertEquals(3, resultado.size(),"ERROR: buscarCursosPorNivel() debe devolver todos los cursos con la temática y nivel especificados.");
        // Si tienen la misma valoración, mantienen el orden original
        assertEquals(curso1, resultado.get(0));
        assertEquals(curso2, resultado.get(1));
        assertEquals(curso3, resultado.get(2));
        
    }


    @Test
    void test_given_listatematica_contains_tematica_and_sublist_with_courses_with_different_valoracion() {
      
    	// Todos los cursos tienen diferente valoracion.
        Curso curso1 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 3, 2);
        Curso curso2 = new Curso(NOMBRE_EJEMPLO + "2", TEMATICA_EJEMPLO, 4, 3);
        Curso curso3 = new Curso(NOMBRE_EJEMPLO + "3", TEMATICA_EJEMPLO, 5, 1);
        listaCursos.addCurso(curso1);
        listaCursos.addCurso(curso2);
        listaCursos.addCurso(curso3);
        
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2);
        assertEquals(3, resultado.size(), "ERROR: buscarCursosPorNivel() debe devolver todos los cursos.");
        
        // Deben estar ordenados por valoración ascendente.
        assertEquals(curso1, resultado.get(0));
        assertEquals(curso2, resultado.get(1));
        assertEquals(curso3, resultado.get(2));
    }

    @Test
    void test_given_listatematica_founds_tematica_at_second_loop_iteration() {
        
        Curso curso1 = new Curso(NOMBRE_EJEMPLO, "Temática 2", 2, 3);
        Curso curso2 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 2, 3);
        listaCursos.addCurso(curso1);
        listaCursos.addCurso(curso2);
        
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2);
        
        assertEquals(1, resultado.size(), "ERROR: buscarCursosPorNivel() debe encontrar la temática en la segunda iteración.");
        assertEquals(resultado.get(0), curso2, "ERROR: buscarCursosPorNivel() debe encontrar la temática en la segunda iteración.");
    }

 
    @Test
    void test_given_listatematica_contains_tematica_and_sublist_with_courses_with_minimum_level_and_courses_with_not_minimum_level_buscarCursosPorNivel() {
        
    	// Varios cursos, con niveles mixtos.z
        Curso curso1 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 2, 3);
        Curso curso2 = new Curso(NOMBRE_EJEMPLO, "Temática 2", 2, 2);
        Curso curso3 = new Curso(NOMBRE_EJEMPLO, TEMATICA_EJEMPLO, 2, 1);
        Curso curso4 = new Curso(NOMBRE_EJEMPLO, "Temática 4", 2, 1);
        
        
        listaCursos.addCurso(curso1); 
        listaCursos.addCurso(curso2); 
        listaCursos.addCurso(curso3); 
        listaCursos.addCurso(curso4); 
        
    
        List<Curso> resultado = listaCursos.buscarCursosPorNivel(TEMATICA_EJEMPLO, 2);
        
        
        assertEquals(1, resultado.size(), "ERROR: buscarCursosPorNivel() debe devolver solo los cursos que superan el nivel mínimo.");
     
        assertEquals(resultado.get(0), curso3, "ERROR: buscarCursosPorNivel() debe devolver los cursos encontrados en el orden ascendente.");
        assertEquals(resultado.get(1), curso1, "ERROR: buscarCursosPorNivel() debe devolver los cursos encontrados en el orden ascendente.");
        
    }
    
    
    
    
    
    /**
     * ***************************** PRUEBAS UNITARIAS DESTINADAS A PROBAR LOS CAMINOS BÁSICOS DE contarCursosPorNivel() *****************************
     */
    
    @Test
    void test_given_listatematicas_empty_then_return_zero() {
  
        int resultado = listaCursos.contarCursosPorNivel("Ejemplo", 2);
        assertEquals(0, resultado, "ERROR: ListaCursos() debe devolver cero si listaTematicas se encuentra vacía.");
    }
    
    @Test
    void test_given_listatematicas_not_contains_tematica_then_return_zero() {
        
    	// Se añade un curso a la lista para que no esté vacía, pero su temática es distinta a la especificada.
        Curso curso = new Curso("Ejemplo 1", "Temática 1", 2, 4);
        listaCursos.addCurso(curso); // Este método añade el curso sin importar si la temática exista en listaTemáticas o no previamente.
        int resultado = listaCursos.contarCursosPorNivel("Temática 2", 2);     
        assertEquals(0, resultado, "ERROR: ListaCursos() debe devolver cero si listaTematicas no contiene la temática especificada.");
    }
    
    @Test
    void test_given_listatematicas_contains_tematica_and_sublist_with_not_minimum_course_level_then_return_zero() {
    	// Se crea un curso con la temática a buscar, pero con un nivel inferior al mínimo.
        Curso curso = new Curso("Ejemplo 1", "Temática 1", 1, 4);
        listaCursos.addCurso(curso); // Este método añade el curso sin importar si la temática exista en listaTemáticas o no previamente.
        int resultado = listaCursos.contarCursosPorNivel("Temática 1", 2); //
        assertEquals(0, resultado, "ERROR: ListaCursos() debe devolver cero si listaTematicas contiene un curso con esa temática pero con un nivel inferior al mínimo.");
    }
    

    @Test
    void test_given_listatematica_contains_tematica_and_sublist_with_minimum_course_level() {
       
    	// Se crea un curso con la temática a buscar, pero con el nivel mínimo.
        Curso curso = new Curso("Ejemplo 1", "Temática 1", 2, 4);
        listaCursos.addCurso(curso); // Este método añade el curso sin importar si la temática exista en listaTemáticas o no previamente.
        int resultado = listaCursos.contarCursosPorNivel("Temática 1", 2); //
        assertEquals(1, resultado, "ERROR: ListaCursos() debe devolver 1 si listaTematicas contiene un curso con esa temática y con el nivel mínimo.");
    }
    
    @Test
    void test_given_listatematica_contains_tematica_and_sublist_with_courses_with_minimum_level_and_courses_with_not_minimum_level_contarCursosPorNivel() {
    	// Se crean varios cursos con la misma temática, pero algunos no tienen el nivel mínimo.
        Curso curso1 = new Curso("Ejemplo 1", "Temática 1", 1, 4);
        Curso curso2 = new Curso("Ejemplo 2", "Temática 1", 2, 4);
        Curso curso3 = new Curso("Ejemplo 3", "Temática 1", 3, 4);
        listaCursos.addCurso(curso1);
        listaCursos.addCurso(curso2);
        listaCursos.addCurso(curso3);
        int resultado = listaCursos.contarCursosPorNivel("Temática 1", 2); //
        assertEquals(2, resultado, "ERROR: ListaCursos() debe contar únicamente aquellos cursos que tengan al menos el nivel mínimo especificado.");
    }
   
   
import com.formandera.domain.models.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Casos de prueba para ListaCursos basados en el análisis de caja blanca (docu.md).
 *
 */
public class ListaCursosTest {

    private ListaCursos listaCursos;
    private Curso info1, info2, info3;
    private Curso sociales1, sociales2;

    @BeforeEach
    void setUp() {
        listaCursos = new ListaCursos();
        
        // Cursos de Informática
        info1 = new Curso("Java Básico", "Informatica", 1, 4);
        info2 = new Curso("Java Intermedio", "Informatica", 2, 5);
        info3 = new Curso("Java Avanzado", "Informatica", 3, 3);
        
        // Cursos de Sociales
        sociales1 = new Curso("Historia Antigua", "Sociales", 1, 3);
        sociales2 = new Curso("Sociología Moderna", "Sociales", 2, 4);

        // Añadimos cursos
        listaCursos.addCurso(info1);
        listaCursos.addCurso(info2);
        listaCursos.addCurso(info3);
        listaCursos.addCurso(sociales1);
        listaCursos.addCurso(sociales2);
    }

    // --- Pruebas para cursosPendientesPorTematica (Caminos Básicos) ---

    // Camino 1: 1 → 2(Fin) → 7 → 8 → 9(Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 1): Sin realizados y sin cursos en lista")
    void test_Pendientes_SinRealizados_SinCursos() {
        // Este test requiere una lista vacía
        ListaCursos listaVacia = new ListaCursos();
        List<Curso> realizados = new ArrayList<>(); // Sin realizados
        
        List<Curso> resultado = listaVacia.cursosPendientesPorTematica(realizados, "Informatica");
        
        // maxNivel se mantiene en 0. Devuelve [].
        assertTrue(resultado.isEmpty());
    }

    // Camino 2: 1 → 2(Loop) → 3(F) → 6 → 2(Fin) → 7 → 8 → 9(Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 2): Realizados de otra temática, sin cursos en lista")
    void test_Pendientes_RealizadosOtraTematica_SinCursos() {
        // Este test requiere una lista vacía
        ListaCursos listaVacia = new ListaCursos();
        List<Curso> realizados = List.of(sociales1); // realizado de sociales
        
        List<Curso> resultado = listaVacia.cursosPendientesPorTematica(realizados, "Informatica");
        
        // maxNivel se mantiene en 0. Devuelve [], temática no coincide, no hay cursos. Devuelve []
        assertTrue(resultado.isEmpty());
    }

    // Camino 3: 1 → 2(Loop) → 3(T) → 4 → 5 → 8 → 9(Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 3): Realizados de la temática, sin cursos en lista")
    void test_Pendientes_RealizadosTematicaOk_SinCursos() {
        // Este test requiere una lista vacía
        ListaCursos listaVacia = new ListaCursos();
        List<Curso> realizados = List.of(info1); // realizado de informática
        
        List<Curso> resultado = listaVacia.cursosPendientesPorTematica(realizados, "Informatica");
        
        // maxNivel se establece a 1, listaCursos está vacía, devuelve [].
        assertTrue(resultado.isEmpty());
    }

    // Camino 4: 1 → 2(Fin) → 7 → 8 → 9(Loop) → 10,11(F) → 17 → 9(Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 4): Sin realizados, cursos de otra temática")
    void test_Pendientes_SinRealizados_CursosOtraTematica() {
        List<Curso> realizados = new ArrayList<>(); // Sin realizados
        
        // Buscamos una temática que no existe ("Salud")
        List<Curso> resultado = listaCursos.cursosPendientesPorTematica(realizados, "Salud");
        
        // maxNivel=0. El IF(10,11) siempre será Falso. Devuelve [].
        assertTrue(resultado.isEmpty());
    }

    // Camino 5: 1 → 2(Fin) → 7 → 8 → 9(Loop) → 10,11(T) → 12(Fin) → 16 → 17 → 9(Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 5): Sin realizados, sublista de temática vacía")
    void test_Pendientes_SinRealizados_SublistaVacia() {
        // Este test requiere una sublista vacía
        ListaCursos listaConTemaSinCurso = new ListaCursos();
        listaConTemaSinCurso.addTematica("Informatica"); // Estado: listaTematicas=[ [] ]
        List<Curso> realizados = new ArrayList<>(); // Estado: realizados=[]
        
        // Tematica="Informatica". maxNivel=0.
        List<Curso> resultado = listaConTemaSinCurso.cursosPendientesPorTematica(realizados, "Informatica");
        
        // El IF del nodo (10,11) es True, pero el FOR(12) está vacío. Devuelve []
        assertTrue(resultado.isEmpty());
    }

    // Camino 6: 1 → 2 (Loop) → 3 (T) → 4 → 5 → 8 → 9 (Loop) → 10,11 (T) → 12 (Loop) → 13 (F) → 15 → 12 (Fin) → 16 → 17 → 9 (Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 6): Realizado N1, Curso N1")
    void test_Pendientes_RealizadosN1_CursoN1() {
        // Usamos un lista local para limitar el número de cursos
        ListaCursos listaConInfo1 = new ListaCursos();
        listaConInfo1.addCurso(info1); // Estado: listaTematicas=[ [info1] ]
        
        List<Curso> realizados = List.of(info1); // Estado: realizados=[info1]
        
        List<Curso> resultado = listaConInfo1.cursosPendientesPorTematica(realizados, "Informatica");
        
        // maxNivel=1.
        // IF del nodo 13 (c.getNivel() > maxNivel) -> (1 > 1) es FALSO.
        assertTrue(resultado.isEmpty());
    }

 // Camino 7: 1 → 2 (Loop) → 3 (T) → 4 → 5 → 8 → 9 (Loop) → 10,11 (T) → 12 (Loop) → 13 (T) → 14 → 15 → 12 (Fin) → 16 → 17 → 9 (Fin) → 18 → 19 → Fin
    @Test
    @DisplayName("Pendientes (Camino 7): Realizado N1, Curso N2")
    void test_Pendientes_RealizadosN1_CursoN2() {
        // Usamos un lista local para limitar el número de cursos
    	ListaCursos listaConInfo1y2 = new ListaCursos();
        listaConInfo1y2.addCurso(info2);
        List<Curso> realizados = List.of(info1); // Estado: realizados=[info1]
        
        List<Curso> resultado = listaConInfo1y2.cursosPendientesPorTematica(realizados, "Informatica");
        
        // maxNivel=1.
        // IF del nodo 13 (info1.getNivel() > 1) -> (1 > 1) es FALSO.
        // IF del nodo 13 (info2.getNivel() > 1) -> (2 > 1) es CIERTO.
        
        // El resultado esperado es [info2]
        
        assertAll("Verificar resultado",
            () -> assertThat("El tamaño de la lista debe ser 1 (N2)", 
                             resultado, hasSize(1)),
            () -> assertThat("La lista debe contener a info2", 
                             resultado, contains(info2))
        );
    }


    // --- Pruebas Adicionales para detectar el BUG de cursosPendientesPorTematica ---

    /**
     * El método toma el nivel del primer curso que lee (N1) y
     * llama 'break', de modo que ignora el resto de cusos (pe. N2).
     *
     * - Entrada -> info1 (N1), info2 (N2)
     * 
     * - Comportamiento ERRÓNEO (código original):
     * maxNivel = 1
     * Devuelve cursos > 1: [info2, info3].
     *
     * - Comportamiento ESPERADO (corregido):
     * maxNivel = 2
     * Devuelve cursos > 2: [info3].
     */
    @Test
    @DisplayName("Pendientes: Detecta el maxNivel correcto si los cursos realizados están desordenados")
    void test_Pendientes_RealizadosDesordenados_DetectaMaxNivel() {
        // Como entrada se le pasa una lista cuyo primer elemento tiene un nivel menor.
        List<Curso> realizados = List.of(info1, info2);
        
        List<Curso> resultado = listaCursos.cursosPendientesPorTematica(realizados, "Informatica");

        assertAll("Verificar resultado",
            () -> assertThat("Debería encontrar solo 1 curso pendiente (N3)",
                             resultado, hasSize(1)),
            () -> assertThat("El único curso pendiente debe ser info3",
                             resultado, contains(info3))
        );
    }
    
    // --- Pruebas para Cobertura de Condicionales ---

    /**
     * Prueba el caso (c.getNivel() == maxNivel) en el primer bucle (maxNivel).
     * - real = [info1, info1_otro] (dos cursos de N1)
     * - Condición (1er loop, 1a vez): (1 > 0) -> CIERTO. maxNivel = 1.
     * - Condición (1er loop, 2a vez): (1 > 1) -> FALSO.
     */
    @Test
    @DisplayName("Condición MaxNivel (F): Realizados N1 y N1_bis")
    void test_Condicion_MaxNivel_RealizadosN1_N1bis() {
        Curso info1_1 = new Curso("Info-1B", "Informatica", 1, 10);
        ListaCursos listaVacia = new ListaCursos();
        List<Curso> realizados = List.of(info1, info1_1); 
        
        List<Curso> resultado = listaVacia.cursosPendientesPorTematica(realizados, "Informatica");
        
        assertTrue(resultado.isEmpty());
    }

    /**
     * Prueba el caso (c.getNivel() < maxNivel) en el PRIMER bucle (maxNivel).
     * - real = [info2, info1] (N2 y luego N1)
     * - Condición (1er loop, 1a vez): (2 > 0) -> CIERTO. maxNivel = 2.
     * - Condición (1er loop, 2a vez): (1 > 2) -> FALSO.
     */
    @Test
    @DisplayName("Condición MaxNivel (F): Realizados N2 y N1")
    void test_Condicion_MaxNivel_RealizadosN2_N1() {
        ListaCursos listaVacia = new ListaCursos();
        List<Curso> realizados = List.of(info2, info1); 
        
        List<Curso> resultado = listaVacia.cursosPendientesPorTematica(realizados, "Informatica");
        
        assertTrue(resultado.isEmpty());
    }
    
    // --- Pruebas para buscarCursoMejorValorado (Caminos Básicos) ---

    /**
     * Camino 1: 1 → 2(Fin) → 14 → 15 → Fin
     *
     */
    @Test
    @DisplayName("MejorValorado (Camino 1): Lista vacía devuelve null")
    void test_MejorValorado_ListaVacia() {
    	// Este test requiere una lista vacía
        ListaCursos listaVacia = new ListaCursos();
        
        Curso resultado = listaVacia.buscarCursoMejorValorado("Info", 1);
        
        assertNull(resultado, "Una lista vacía debe devolver null");
    }

    /**
     * Camino 2: 1 → 2(Loop) → 3,4(F) → 13 → 2(Fin) → 14 → 15 → Fin
     */
    @Test
    @DisplayName("MejorValorado (Camino 2): Temática no encontrada devuelve null")
    void test_MejorValorado_TematicaNoEncontrada() {
        // Usamos la lista del setUp, pero buscamos una temática que no existe
        
        Curso resultado = listaCursos.buscarCursoMejorValorado("Salud", 1);
        
        // El bucle (2) itera para "Informatica" (F) y "Sociales" (F)
        assertNull(resultado, "Una temática no encontrada debe devolver null");
    }

    /**
     * Camino 3: 1 → 2(Loop) → 3,4(T) → 5(Fin) → 11 → 12 → 14 → 15 → Fin
     * (Camino No Factible)
     * Justificación: Si (3,4) es T, la sublista no está vacía,
     * por lo que el bucle (5) debe ejecutarse al menos una vez.
     */

    /**
     * Camino 4: 1 → 2(Loop) → 3,4(T) → 5(Loop) → 6(F) → 10 → 5(Fin) → 11 → 12 → 14 → 15 → Fin
     */
    @Test
    @DisplayName("MejorValorado (Camino 4): Nivel no encontrado devuelve null")
    void test_MejorValorado_TematicaOk_NivelNoEncontrado() {
        // Usamos la lista del setUp. Buscamos un nivel que no existe
        
        Curso resultado = listaCursos.buscarCursoMejorValorado("Informatica", 99);
        
        // El bucle (5) itera, pero (6) siempre es Falso.
        assertNull(resultado, "Un nivel no encontrado debe devolver null");
    }

    /**
     * Camino 5: 1 → 2(Loop) → 3,4(T) → 5(Loop) → 6(T) → 7(F) → 9 → 10 → 5(Fin) → 11 → 12 → 14 → 15 → Fin
     * (Camino No Factible)
     * Justificación: En la primera iteración de (5) que cumple (6),
     * 'mejor' es null, por lo que (7) (mejor == null) siempre será T.
     */

    /**
     * Camino 6: 1 → 2(Loop) → 3,4(T) → 5(Loop) → 6(T) → 7(T) → 8 → 9 → 10 → 5(Fin)→ 11 → 12 → 14 → 15 → Fin
     */
    @Test
    @DisplayName("MejorValorado (Camino 6): Encuentra el curso de un nivel específico")
    void test_MejorValorado_TematicaOk_UnCursoOk() {
        // info1(N1, V4), info2(N2, V5), info3(N3, V3)
        
        Curso resultado = listaCursos.buscarCursoMejorValorado("Informatica", 2);
        
        // El bucle (5) itera:
        // info1: 6(F)
        // info2: 6(T), 7(T) -> mejor = info2
        // info3: 6(F)
        // Bucle (5) termina. 'break' (12). Devuelve info2.
        
        assertEquals(info2, resultado, "Debe devolver el curso de Nivel 2 (info2)");
    }
   
}