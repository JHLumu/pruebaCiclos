package com.formandera.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuscadorCursosSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    /* @BeforeEach
    void setup() {
    	// Indica a Selenium dónde está el ejecutable de ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver"); //<--- ADAPTAR A VUESTRA RUTA
        // System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe"); //<--- ADAPTAR A VUESTRA RUTA
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/descuento");
    }*/
    
    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    
    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/buscador");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    /**
     * Prueba parametrizada de casos válidos e inválidos
     *
     * Parámetros:
     * edad, temática, puntuacionMinima, cursosCompletados, descuentoEsperado
     */
    @ParameterizedTest(name = "Caso {index}: edad={0}, tematica={1}, puntuacion={2}, cursos={3}, descuentoEsperado={4}")
    @CsvSource({
            // Casos válidos
            "25, Programación, 4, 3, 0",
    })
    void testGivenEdadTamaticaPuntuacionValidosWhenBuscarCursosThenMuestraCursosYPorcentajeDescuentoAplicadoCoinicideConDescuentoEsperado(String edad, String tematica, String puntuacionMinima,
                                      String cursosCompletados, double descuentoEsperado) {

        // 1. Introducimos datos en los campos
        WebElement campoEdad = driver.findElement(By.id("edad"));
        WebElement campoTematica = driver.findElement(By.id("tematica"));
        WebElement campoPuntuacion = driver.findElement(By.id("puntuacionMinima"));
        WebElement campoCursos = driver.findElement(By.id("cursosCompletados"));
        WebElement botonBuscar = driver.findElement(By.id("btnBuscar"));

        campoEdad.clear(); campoEdad.sendKeys(edad);
        campoTematica.clear(); campoTematica.sendKeys(tematica);
        campoPuntuacion.clear(); campoPuntuacion.sendKeys(puntuacionMinima);
        campoCursos.clear(); campoCursos.sendKeys(cursosCompletados);

        // 2. Enviamos el formulario
        botonBuscar.click();

        // 3. Verificar resultado esperado
        // Esperamos a tabla de resultados o alerta OK (mensaje verde superior)
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alertaOk = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("alertOk"))
        );
        assertTrue(alertaOk.isDisplayed(), "No se mostró el mensaje de éxito.");
        // Además, comprobar que hay una tabla de resultados
        WebElement tabla = driver.findElement(By.cssSelector(".result-card table"));
        assertTrue(tabla.isDisplayed(), "No se mostró la tabla de resultados.");
            
        List<WebElement> filas = tabla.findElements(By.cssSelector("tbody tr"));
        if (filas.isEmpty()) {
            fail("No se devolvió ningún curso en la búsqueda.");
         }
            
         // Recorremos las filas de la tabla para comprobar que los descuentos se han aplicado correctamente
        for (WebElement fila : filas) {
            List<WebElement> columnas = fila.findElements(By.tagName("td"));

            if (columnas.size() >= 4) {
                String nombreCurso = columnas.get(0).getText();
                double precioBase = Double.parseDouble(columnas.get(2).getText());
                double precioConDescuento = Double.parseDouble(columnas.get(3).getText());

                double ratio = precioConDescuento / precioBase;
                double descuentoReal = (1 - ratio) * 100;

                // Aceptamos un margen de ±0.5 % por redondeo
                assertEquals(descuentoEsperado, descuentoReal, 0.5,
                         "El descuento aplicado no coincide con el esperado para el curso " + nombreCurso);
            }
        }
    }

/**
 * Prueba parametrizada de casos inválidos
 *
 * Parámetros:
 * edad, temática, puntuacionMinima, cursosCompletados, resultadoEsperado
 */
@ParameterizedTest(name = "Caso {index}: edad={0}, tematica={1}, puntuacion={2}, cursos={3}, descuentoEsperado={4}")
@CsvSource({
        // Casos inválidos
        "10, Programación, 4, 3, Error: La edad mínima es 12 años",      			// Edad < 12
})
void testGivenEdadTamaticaOPuntuacionInvalidoWhenBuscarCursosThenMuestraMensajeErrorEsperado(String edad, String tematica, String puntuacionMinima,
                                  String cursosCompletados, String resultadoEsperado) {

    // 1. Introducimos datos en los campos
    WebElement campoEdad = driver.findElement(By.id("edad"));
    WebElement campoTematica = driver.findElement(By.id("tematica"));
    WebElement campoPuntuacion = driver.findElement(By.id("puntuacionMinima"));
    WebElement campoCursos = driver.findElement(By.id("cursosCompletados"));
    WebElement botonBuscar = driver.findElement(By.id("btnBuscar"));

    campoEdad.clear(); campoEdad.sendKeys(edad);
    campoTematica.clear(); campoTematica.sendKeys(tematica);
    campoPuntuacion.clear(); campoPuntuacion.sendKeys(puntuacionMinima);
    campoCursos.clear(); campoCursos.sendKeys(cursosCompletados);

    // 2. Enviamos el formulario
    botonBuscar.click();

    // 3. Verificar resultado esperado
    
        // Esperar a que aparezca alerta de error
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement alerta = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("alertError"))
    
    );
    assertTrue(alerta.isDisplayed(), "No se mostró el mensaje de error esperado.");
    assertEquals(resultadoEsperado,alerta.getText(), "El mensaje de alerta no coincide con el esperado.");
 } 
}

