package com.formandera.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountFormSeleniumTest {

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
        driver.get("http://localhost:8080/descuento");
    }

    @AfterEach
    void tearDown() {
    	if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testGivenPremium21CursosAnd100PrecioBaseWhenCalcularDescuentoThenDescuentoIgual25PrecioFinal75() {
        driver.findElement(By.id("nCursosPrevios")).sendKeys("21");
        driver.findElement(By.id("esPremium")).click();
        driver.findElement(By.id("precioBase")).sendKeys("100");
        driver.findElement(By.id("btnCalcular")).click();

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement porcentaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("porcentaje")));

        WebElement precioFinal = driver.findElement(By.id("precioFinal"));
        assertEquals("25.0", porcentaje.getText(), 
            "Se esperaba 25% para usuario premium con 21 cursos");
        assertEquals("75.0", precioFinal.getText(), 
        		"Se esperaba precio 75 para usuario premium con 21 cursos");
    }
    
    @Test
    void testGivenPremiumMenos3CursosAnd100PrecioBaseWhenCalcularDescuentoThenMensajeError() {
        driver.findElement(By.id("nCursosPrevios")).sendKeys("-3");
        driver.findElement(By.id("esPremium")).click();
        driver.findElement(By.id("precioBase")).sendKeys("100");
        driver.findElement(By.id("btnCalcular")).click();

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alert"))); 
        
        assertEquals("Error: El número de cursos no puede ser negativo.", alerta.getText(), 
            "Se esperaba un mensaje de error por número de cursos negativos");
    }
}


