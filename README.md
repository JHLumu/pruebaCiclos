# Práctiac 6 - Calidad de Software 25/26
# Introducción a TDD. Uso de mocks y stubs

**Alumno 1**: Jesús Sánchez Pardo, <wjesus.sanchez@um.es>  

**Alumno 2**: Jiahui Lin, <jiahui.lin@um.es>  


| Funcionalidad | Rama                 | Tester    | Developer |
| ------------- | -------------------- | --------- | --------- |
| A             | `feature/ordenacion` | Jesús | Jiahui |
| B             | `feature/seleccion`  | Jiahui | Jesús |



## Proyecto formandera.com — Módulo de Admisiones
La plataforma formandera.com es un sistema de gestión integral para la formación continua de profesionales. En ella se publican convocatorias de cursos, cada una con un número variable de plazas, una temática concreta y un conjunto de usuarios interesados en inscribirse.
A medida que la plataforma ha crecido y se ha extendido entre distintos colectivos profesionales, han aparecido nuevas necesidades relacionadas con la gestión justa, transparente y automatizada de estas inscripciones.

En la actualidad, las convocatorias más demandadas suelen recibir más solicitudes que plazas disponibles, y la asignación manual resulta inviable:
hay que aplicar criterios coherentes, registrar los cambios, evitar errores y ofrecer una experiencia totalmente fiable al usuario.

Por ello, el equipo de formandera.com está diseñando un módulo de Admisiones capaz de:

- ordenar a los candidatos según criterios de prioridad (experiencia, crédito formativo, antigüedad, etc.),
- seleccionar automáticamente a los admitidos, respetando las plazas máximas,
- adaptarse a reglas de negocio cambiantes,
- minimizar errores de implementación mediante TDD,
- y estar preparado para integrarse en una arquitectura hexagonal, que garantiza independencia entre el núcleo de negocio y la infraestructura técnica.

En este contexto, la práctica consistirá en desarrollar las dos piezas básicas del módulo de admisiones:

- un servicio de ordenación de inscripciones según criterios de prioridad, y
- el caso de uso asociado a la admisión de alumnos en convocatorias integrado con un repositorio externo.

Ambas funcionalidades deben implementarse utilizando Desarrollo Guiado por Pruebas (TDD), trabajando en ramas separadas, utilizando stubs y/o mocks y
respetando la estructura modular del proyecto según los principios de una arquitectura limpia.

---
## Objetivos de la práctica

1. Aplicar Desarrollo Guiado por Pruebas (TDD) de forma rigurosa.
2. Trabajar de forma colaborativa mediante:

  - ramas diferenciadas
  - roles diferenciados
  - commits estructurados (TESTi / DEVi / REFACTORi)
  - pull requests

3. Construir código bajo una arquitectura limpia mínima.
4. Utilizar stubs (dobles controlados) y mocks (Mockito) en las pruebas.
5. Gestionear adecuadamente la integración en GitHub.

---
## Descripción general del proyecto

Se deberán incorporar dos funcionalidades relacionadas con el sistema de convocatorias de formandera.com:

### Funcionalidad A — Ordenación de Inscripciones
El servicio para ordenar inscripciones devolverá una nueva lista con las incripciones ordenadas siguiendo los criterios de prioridad siguientes (el método de ordenación no debe modificar la lista original recibida como parámetro):
1. Mayor crédito
2. Mayor número de cursos en la temática
3. Mayor antigüedad en la plataforma
4. Menor ID (desempate final)

Se desarrollará en la rama `feature/ordenacion`

### Funcionalidad B — Caso de uso "Selección de Admitidos"

Dada una convocatoria, el caso de uso debe:

1. Obtener la convocatoria del repositorio de convocatorias.
2. Aplicar ordenación de las inscripciones.
3. Seleccionar los primeras N inscripciones.
4. Descontar crédito e incrementar cursos en temática.
5. Lanzar excepción si ningún usuario es admisible.

Se desarrolla en la rama `feature/seleccion`

---
## Organización del trabajo
Si el grupo está formado por 2 personas, se deben asignan roles complementarios y rotatorios:
| Funcionalidad | Rama                 | Tester    | Developer |
| ------------- | -------------------- | --------- | --------- |
| A             | `feature/ordenacion` | Persona A | Persona B |
| B             | `feature/seleccion`  | Persona B | Persona A |

Si el grupo es individual:
La persona deberá realizar ambas ramas, alternando los citados roles internamente.

## Flujo GitHub obligatorio
1. `git branch feature/ordenacion`.
2. `git branch feature/seleccion`.
3. Cada persona trabaja como desarrollador solo en su `rama`.
4. Cada persona trabaja como tester en la `rama` complementaria.
5. Uso obligatorio de commits e identificación de comentarios:
- TESTi → test en rojo: Usaremos la clave TESTi, donde i representa el número de iteración, para identificar el commit en el que añadimos un nuevo test que falla.
- DEVi → código mínimo para verde: usaremos la clave DEVi, donde i es el número asociado al TESTi, cada vez que añadamos un commit para incorporar la funcionalidad que hace que el TESTi deje de fallar.
- REFACTORi → mejora interna sin romper tests: usaremos la clave REFACTORi, donde i es el numero asociado al DEVi que da origen a la refactorización, delante de la identificación del proceso de refactorización cuando sea necesario.
- Documentar en el archivo md de documentación de la rama correspondiente el ciclo TDD realizado con indentificación de los commits. Se debe indicar: Iteración i - TESTi (Enlace a NUM COMMIT-TESTi) - DEVi (Enlace a NUM COMMIT-DEVi) - REFACTORi (Si existe, enlace a NUM COMMIT-REFACTORi)
6. Al finalizar cada rama → Pull Request
7. Revisión por parte del compañero
8. Merge a main
## Reglas estrictas para TDD y commits
- Un commit TESTi solo puede añadir tests que fallen. El commit se identificará con el prefijo `TESTi` y una frase o comentario descriptivo. Por ejemplo: `commit -m "TEST1 - Costructor..."`
- Un commit DEVi solo puede contener el código mínimo para pasar los tests del TESTi correspondiente. El commit se identificará con el prefijo `DEVi` seguido de una frase o comentario descriptivo. Por ejemplo, `commit -m "DEV1 - Se añade el contructor. pasa a verde TEST1"`. 
- Un commit REFACTORi no puede cambiar comportamiento observable. El commit se identificará con el prefijo `REFACTORi`seguido de una frase o comentario descriptivo. Por ejemplo, `commit -m "REFACTOR12- Se extrae el método x(), se realiza despues de DEV12"`.
- Está prohibido:
  - mezclar test y producción en un commit
  - añadir dos tests en el mismo TESTi
  - escribir más código del necesario en DEVi
  - saltarse un paso
- Es opcional, pero recomendable cuando sea preciso, el identificar vías de refactorización.

---
## Arquitectura hexagonal

El codigo se estructura de la siguiente forma:
```
app/
├── domain/
│   ├── model/
│   │   ├── Usuario.java
│   │   ├── IConvocatoria.java
│   │   └── IInscripcion.java
│   ├── repositories/
│       └── IConvocatoriaRepository.java
│   └── services/
│       ├── OrdenadorInscripcionesService.java
│       └── ISelectorAdmisionesDomainService.java
│
├── application/
│   ├── use_cases/
│   │   └── SelectorAdmisionesUseCase.java
│
└── ...
```

---
## Requisitos de tests

Para la implementación de los test se deberá identificar la estrategia de suplantación que mejor proceda (stubs o mocks) de las clases o servicios que no estén a disposición en el desarrollo del código objeto de la práctica.

## Sobre la aquitectura y guía para identificar mocks y stubs

### Pautas para identificar dependencias a suplantar

Para poder aplicar TDD correctamente y evitar acoplamientos indebidos durante las pruebas, debéis tener en cuenta que:

#### 1. TDD requiere aislar las clases bajo prueba

El objetivo es escribir tests centrados únicamente en la clase que estamos desarrollando. Esto significa: si una clase depende de otra, y esa otra no forma parte de la lógica que estamos probando, debe sustituirse por un doble controlado (stub o mock).

#### 2. ¿Cómo identificar qué sustituir?

Analiza las funcionalidades a incorporar:

**Funcionalidad A: OrdenadorInscripcionesService**

Responde a las siguientes preguntas:

¿Qué objetos necesito para poder ordenar? ¿Tiene comportamiento complejo?

Si necesitas suplantar algo, recuerda que debes usar un STUB cuando la dependencia tiene un estado interno (desde el punto de vista de nuestro modelo de dominio, que se quiere consultar. No uses un stub si la clase a suplantar tiene asignadas responsabilidades de coordinación entre capas, depende de otras capas, accede a la infraestructura o toma decisiones dentro del modelo de negocio.

**Funcionalidad B: SelectorAdmisionesUseCase**
Clase bajo prueba: `SelectorAdmisionesUseCase`

¿Qué dependenciás se plantean en el caso de uso a desarrollar?¿Cuáles tengo que suplantar (aunque tenga implementación)?

*En un caso de uso NUNCA se debe llamar a infraestructura real. Los tests del caso de uso deben aislarse completamente de servicios y repositorios.*

### Guía para diseñar los tests del caso de uso

#### 1. El caso de uso no implementa reglas de negocio

El caso de uso coordina, no calcula. Por tanto debe probar:

  1. que llama al repositorio,
  2. que llama al servicio de ordenación,
  3. que pasa la lista ordenada al servicio de dominio,
  4. que devuelve el valor que devuelve el dominio.

#### 2. Preguntas que te ayudarán en el proceso:

  - ¿La clase que estás probando debe ordenar inscripciones, o debe pedirlo fuera?
  - ¿Debe el caso de uso crear objetos o recibirlos ya construidos?
  - ¿Qué pasaría si el repositorio fallara? ¿Lo necesitas para el test?
  - ¿Cómo puedes comprobar que el caso de uso está llamando a otro servicio sin que ese servicio se ejecute realmente?
  - ¿Qué dobles de prueba permiten verificar llamadas?  

---
## Ejercicios

1. Crea y configura el repositorio y del proyecto.
2. Importa el proyecto Maven que encontrarás en la carpeta `tdd-admisiones` de este repositorio.
3. Crea las dos ramas de trabajo: `feature/ordenacion`y `feature/seleccion`
4. Implementación TDD del servicio de ordenación.

   a)  Crear un documento md en la rama `feature/ordenacion` donde se indique la persona que ejerce el rol de tester y el rol de desarollador (si se trata de un grupo individual no es necesairo indicarlo)
   
   b) Se debe aplicar TDD para implementar la funcionalidad de la clase `OrdenadorInscripcionesService`. Antes de comenzar identifica las posibles clases que habrá que suplantar para las pruebas y el desarrollo. Incorporalas al documento md junto con una justificación sobre la idoneidad de usar un stub o un mock.

   c) En la clase `OrdenadorInscripcionesService` se debe implementar mediante TDD la lógica de ordenación de inscripciones según los criterios priorizados siguientes:
      - mayor crédito,
      - más cursos en la temática,
      - mayor antigüedad en la plataforma,
      - menor númmero de ID.  
   
   d) La implementación seguirá, obligatoriaments, la secuencia de trabajo:
   
     ```
     i = 1;
     MIENTRAS NO FUNCIONALIDAD COMPLETA HACER:

      Dentro de la sección "Iteraciones de TDD" del documentación (md) de la rama "feature/ordenacion"
      crear un subsección con el título que siga la siguiente estructura: "Iteración " + i

      Indicar la clase de prueba y la clase de desarrollo donde se incorporará el ciclo tdd actual. 
     
      TESTi: Crear el test i-ésimo en rojo según la funcionalidad que se esté implementando
      (Tester de la rama hace: `commit -m "TESTi - Se añade el test .... que falla"`)

      Dentro de la subsección "Iteración "+i del documentación (md) de la rama "feature/ordenacion"
      crear un apartado con un título que siga la siguiente estructura: "TEST" + i + "(enlace COMMIT - TEST" + i + ")".
      Recuerda enlazar el commit donde se incorporó el TESTi correspondiente. En el cuerpo del apartado incorpora el código del TESTi
     
      DEVi: Escribir el código mínimo para pasar TESTi
      (Desarrollador de la rama hace: `commit -m "DEVi - Se añade el código ... que hace que TESTi pase a verder"`)
        
      Dentro de la subsección "Iteración "+i del documentación (md) de la rama "feature/ordenacion",
      debajo del apartado anterior crear un nuevo apartado con un título que siga la siguiente estructura:
      "DEV" + i + "(enlace COMMIT - DEV" + i + ")".
      Recuerda enlazar el commit donde se incorporó el DEV correspondiente.
      En el cuerpo del apartado incorpora el código mínimo que configura el DEVi
     
      REFACTORi: Si procede, se añadia una modificación que mejore la estructura del código sin modificar la funcionalidad y 
haciendo que los test sigan siendo verde (es posible que las factorizaciones afecten a los test).
Si se hace una refactorización, el desarrollador hará: `commit -m "REFACTORi - Se hace la refactorización ... `.
      Si se ha introducido una refactorización, dentro de la subsección "Iteración "+i del documentación (md) de la rama 
      "feature/ordenacion", debajo del apartado anterior crear un nuevo apartado con un título que siga la siguiente estructura: 
      "REFACTOR" + i + "(enlace COMMIT del REFACTORi)". 
      Recuerda enlazar el commit donde se incorporó el REFACTOR correspondiente. 
      En el cuerpo del apartado incorpora una breve explicación de la refactorización realizada y el código resultante de aplicar 
      dicha refactorización al DEVi
     
      i++
     
     FIN_MIENTRAS

     ```
      
5. Implementación TDD del caso de uso completo de selección. Se comenzará por la clase de selección de la capa de aplicación y de irá implementando la lógica del negocio hacia las capas más profundas (TDD deberá mostrar las decisiones necesarias sobre la implementación).

   a) Crear un documento md en la rama `feature/seleccion` donde se indique la persona que ejerce el rol de tester y el rol de desarollador (si se trata de un grupo individual no es necesairo indicarlo)

   b) Se debe aplicar TDD para implementar la funcionalidad derivada del caso de uso `SelectorAdmisionesUseCase`. Antes de comenzar identifica las posibles clases que habrá que suplantar para las pruebas y el desarrollo. Incorporalas al documento md junto con una justificación sobre la idoneidad de usar un stub o un mock.
   Te puede resultar útil, antes de enfrentarte al primer test el comprobar la siguiente lista de cotejo:
   
□ He identificado la clase a probar (SelectorAdmisionesUseCase)

□ He determinado qué responsabilidades NO corresponden a la calse que implementa el caso de uso

□ He revisado qué servicios son externos y deben ser suplantados

□ He preparado stubs solo para objetos de datos 

□ He confirmado que en TEST1 no necesito lógica real de ordenación


   c) Se debe implementar el caso de uso del selector de admisiones mediante TDD y usando arquitectura hexagonal. La lógica a implementar será la siguiente:
      - obtiene las inscripciones desde el repositorio,
      - llama al servicio de dominio para ordenarlas,
      - selecciona las primeras N según las plazas,
      - aplica efectos colaterales (descontar crédito, incrementar cursos),
      - lanza excepción si nadie es admisible. 
   
   d) La implementación seguirá, obligatoriaments, la secuencia de trabajo:
     Antes de comenzar la implementación crear una sección en la documentación titulada: "Iteraciones de TDD"
    
     ```
     i = 1;
     MIENTRAS NO FUNCIONALIDAD COMPLETA HACER:
       Dentro de la sección "Iteraciones de TDD" del documentación (md) de la rama "feature/seleccion"
       crear un subsección con el título que siga la siguiente estructura: "Iteración " + i

       Indicar la clase de prueba y la clase de desarrollo donde se incorporará el ciclo tdd actual. 
       
       TESTi: Crear el test i-ésimo en rojo según la funcionalidad que se esté implementando
       (Tester de la rama hace: `commit -m "TESTi - Se añade el test .... que falla"`)

       Dentro de la subsección "Iteración "+i del documentación (md) de la rama "feature/seleccion" crear un
       apartado con un título que siga la siguiente estructura: "TEST" + i + "(enlace COMMIT - TEST" + i + ")".
       Recuerda enlazar el commit donde se incorporó el TEST correspondiente. En el cuerpo del apartado incorpora el código del TESTi
          
       DEVi: Escribir el código mínimo para pasar TESTi
       (Desarrollador de la rama hace: `commit -m "DEVi - Se añade el código ... que hace que TESTi pase a verder"`)

       Dentro de la subsecciñon "Iteración "+i del documentación (md) de la rama "feature/seleccion", debajo del apartado
       anterior crear un nuevo apartado con un título que siga la siguiente estructura: "DEV" + i + "(enlace COMMIT - DEV" + i + ")".
       Recuerda enlazar el commit donde se incorporó el DEV correspondiente.
       En el cuerpo del apartado incorpora el código mínimo que configura el DEVi
     
       REFACTORi: Si procede, se añadia una modificación que mejore la estructura del código sin modificar la funcionalidad
       y haciendo que los test sigan siendo verde (es posible que las factorizaciones afecten a los test).
       Si se hace una refactorización, el desarrollador hará: commit -m "REFACTORi - Se hace la refactorización ... .
       
       Si se ha introducido una refactorización, dentro de la subsección "Iteración "+i del documentación (md) de la rama 
       "feature/seleccion", debajo del apartado anterior crear un nuevo apartado con un título que siga la siguiente estructura: 
       "REFACTOR" + i + "(enlace COMMIT del REFACTORi)".
       Recuerda enlazar el commit donde se incorporó el REFACTOR correspondiente.
       En el cuerpo del apartado incorpora una breve explicación de la refactorización realizada y
       el código resultante de aplicar dicha refactorización al DEVi
     
       i++
     
     FIN_MIENTRAS

     ```
     
7. En el documento md de documentación de cada rama (los indicados en los apartados 4.a y 5.a) se debe incorporar la siguiente documentación:
    - Roles asumidos en cada rama
    - Justificación del uso de stubs y mocks en la implementación
    - Explicación del ciclo TDD seguido (el contenido indicado en los ejercicios 4.b y 5.b)
    - Principales dificultades encontradas
    - Mejoras propuestas
8. Realizar un merge de las ramas individuales a la principal con el fin de que toda la información quede integrada.

---

## Evaluación

| Criterio                              | Peso    | Indicadores                                                                                                     |
| ------------------------------------- | ------- | --------------------------------------------------------------------------------------------------------------- |
| **Aplicación rigurosa del ciclo TDD y su documentación** | 5 puntos | Commits TESTi/DEVi/REFACTORi; incrementalidad; tests en rojo primero; código mínimo en verde; refactors limpios |
| **Calidad de los tests en el proceso**| 2 puntos     | Uso correcto de stubs y mocks; claridad; capacidad de desarrollo; independencia                                 |
| **Trabajo colaborativo en GitHub**    | 1.5 puntos     | Ramas correctas; Pull Requests; resolución de conflictos; mensajes de commit claros y tipados                   |
| **Refactorización y calidad del código**     | 1 punto     | limpieza; ausencia de duplicación; nombres adecuados; código estructurado                                  |
| **Documentación final**               | 0,5 puntos      | memoria clara, ordenada y con explicación del proceso                                                         |

---

## Entregables

La entrega se realizará en la tarea del Aula Virtual asignada a la práctica con fecha de entrega 12/12/2025 23:55 horas y se debe entregar:

Todos los alumnos (todos los miembros de cada grupo) entregarán el enlace al repositorio de la práctica del grupo
