package com.adidas.tests;

// ============================================================
// IMPORTACIONES: Le decimos a Java qué librerías vamos a usar
// ============================================================
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * =======================================================================
 * PRUEBA AUTOMATIZADA: Flujo de compra
 * =======================================================================
 *
 * Esta clase automatiza el siguiente flujo:
 *   1. Abrir la página principal
 *   2. Iniciar sesión con credenciales
 *   3. Seleccionar un producto
 *   4. Agregar producto al carrito
 *   5. Ir al carrito y proceder al pago
 *   6. Completar formulario de pago y confirmar la compra
 *
 * CONCEPTOS CLAVE QUE VERÁS EN ESTE CÓDIGO:
 *   - WebDriver: el "robot" que controla el navegador
 *   - WebDriverWait: le dice al robot que espere antes de hacer algo
 *   - click(): hace clic en un elemento
 *   - sendKeys(): escribe texto en un campo
 * =======================================================================
 */
public class AdidasCompraTest {

    // ============================================================
    // VARIABLES GLOBALES DE LA CLASE
    // ============================================================

    /** driver es el objeto principal: representa el navegador Chrome que controlaremos */
    private WebDriver driver;

    /**
     * wait es un objeto de "espera inteligente":
     * En lugar de esperar X segundos fijos, espera hasta que algo ocurra
     * (máximo 20 segundos antes de fallar).
     * Esto hace la prueba más robusta porque Adidas puede tardar en cargar.
     */
    private WebDriverWait wait;

    /** Tiempo máximo de espera en segundos para cada elemento */
    private static final int TIEMPO_ESPERA = 20;

    // ============================================================
    // CREDENCIALES DE PRUEBA
    // ============================================================
    private static final String EMAIL    = "seletest";
    private static final String PASSWORD = "bazm2$";


    // ============================================================
    // CONFIGURACIÓN INICIAL (se ejecuta UNA VEZ antes de las pruebas)
    // ============================================================

    /**
     * @BeforeSuite: Este método se ejecuta ANTES de todas las pruebas.
     * Aquí configuramos e iniciamos el navegador.
     */
    @BeforeSuite
    public void configurarNavegador() {

        System.out.println("========================================");
        System.out.println("  Iniciando configuración del navegador ");
        System.out.println("========================================");

        // WebDriverManager descarga automáticamente el ChromeDriver correcto
        WebDriverManager.chromedriver().setup();

        // ChromeOptions nos permite configurar cómo se abre Chrome
        ChromeOptions opciones = new ChromeOptions();

        // IMPORTANTE: Desactivamos la detección de automatización
        // Algunos sitios bloquean Selenium; esto lo hace menos detectable
        opciones.addArguments("--disable-blink-features=AutomationControlled");
        opciones.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        opciones.setExperimentalOption("useAutomationExtension", false);

        // Maximizamos la ventana para tener más espacio y evitar
        // que elementos estén fuera de pantalla
        opciones.addArguments("--start-maximized");

        // Desactivamos notificaciones del navegador (pueden interrumpir la prueba)
        opciones.addArguments("--disable-notifications");

        // Iniciamos Chrome con nuestra configuración
        driver = new ChromeDriver(opciones);

        // Configuramos la espera inteligente (máximo 20 segundos por elemento)
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIEMPO_ESPERA));

        // Tiempo máximo para cargar una página completa (60 segundos)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        System.out.println("✓ Navegador iniciado correctamente");
    }


    // ============================================================
    // LA PRUEBA PRINCIPAL
    // ============================================================

    /**
     * @Test: Esta anotación le dice a TestNG que este método ES una prueba.
     * TestNG lo ejecutará automáticamente.
     */
    @Test
    public void flujoCompletoCompraAdidas() throws InterruptedException {

        // Llamamos a cada paso del flujo en orden
        paso1_AbrirPaginaPrincipal();
        paso2_IniciarSesion();
        paso3_SeleccionarProducto();
        paso4_AgregarAlCarrito();
        paso5_IrAlCarritoYProcederAlPago();
        paso6_CompletarFormularioDePago();

        System.out.println("\n========================================");
        System.out.println("  ✓ PRUEBA COMPLETADA EXITOSAMENTE      ");
        System.out.println("========================================");
    }


    // ============================================================
    // PASO 1: Abrir la página principal de testing
    // ============================================================
    private void paso1_AbrirPaginaPrincipal() {
        System.out.println("\n--- PASO 1: Abriendo paguina ---");

        // driver.get() navega a una URL, igual que escribirla en la barra del navegador
        driver.get("https://www.demoblaze.com/index.html");

        // Esperamos hasta que el título de la página contenga "STORE"
        // Esto confirma que la página cargó correctamente
        wait.until(ExpectedConditions.titleContains("STORE"));

        System.out.println("✓ Página principal cargada: " + driver.getTitle());

        // Pequeña pausa para dejar que los scripts de la página terminen de cargar
        esperarMilisegundos(2000);

        // Intentamos cerrar cualquier popup/banner inicial que aparezca
        cerrarPopupsIniciales();
    }


    // ============================================================
    // PASO 2: Iniciar sesión
    // ============================================================
    private void paso2_IniciarSesion() throws InterruptedException {
        System.out.println("\n--- PASO 2: Iniciando sesión ---");

        // 1. Buscamos el boton
        WebElement loginBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("login2"))
        );

        // 2. Hacer click
        if (loginBtn != null) {
            loginBtn.click();
            System.out.println("✓ Clic en ícono de login");
        }

        // 3. Verificar que el modal se abrió correctamente
        wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("logInModal"))
        );

        esperarMilisegundos(3000);

        // ---- Ingresar el email ----
        WebElement inputEmail = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))
        );
        inputEmail.clear();                    // Limpiamos el campo por si tiene texto previo
        inputEmail.sendKeys(EMAIL);            // Escribimos el email
        System.out.println("✓ Email ingresado");

        esperarMilisegundos(2000);

        // ---- Ingresar la contraseña ----

        WebElement inputPassword = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword"))
        );
        inputPassword.clear();                    // Limpiamos el campo por si tiene texto previo
        inputPassword.sendKeys(PASSWORD);            // Escribimos la contraseña
        System.out.println("✓ Contraseña ingresada");

        esperarMilisegundos(2000);

        // ---- login ----
        
        wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log in']")) // Este boton al no tener ID lo buscamos por su texto visible
        ).click();

        esperarMilisegundos(5000);

    }


    // ============================================================
    // PASO 3: Seleccionar un producto
    // ============================================================
    private void paso3_SeleccionarProducto() throws InterruptedException {
        System.out.println("\n--- PASO 3: Seleccionando producto ---");

        // Nos aseguramos de estar en la página principal
        driver.get("https://www.demoblaze.com/index.html");
        esperarMilisegundos(3000);

        // Cerrar cualquier popup que aparezca al recargar
        cerrarPopupsIniciales();

        // Buscamos cualquier producto en la página
        List<WebElement> productos = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
        By.cssSelector("a[href*='prod.html']")
    )
);

        if (productos.isEmpty()) {
            // Si no encontramos productos en la home, navegamos a una categoría
            System.out.println("! No hay productos visibles en home, buscando en categoría...");
            driver.get("https://www.demoblaze.com/index.html#");
            esperarMilisegundos(3000);

            productos = driver.findElements(
                By.cssSelector("article.product-card, [data-auto-id='product-card'], .product-card, [class*='ProductCard']")
            );
        }

        if (!productos.isEmpty()) {
            // Seleccionamos el primer producto visible
            WebElement primerProducto = productos.get(0);

            // Scrolleamos hasta el producto para asegurarnos de que esté visible
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                primerProducto
            );
            esperarMilisegundos(1000);

            primerProducto.click();
            System.out.println("✓ Producto seleccionado (primer resultado)");
        } else {
            // Último recurso: hacer clic en el primer enlace de producto que encontremos
            System.out.println("! Buscando enlace de producto alternativo...");
            WebElement enlaceProducto = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href*='/product/'], a[href*='/p/']")
                )
            );
            enlaceProducto.click();
            System.out.println("✓ Producto seleccionado por enlace");
        }

        // Esperamos que cargue la página del producto
        esperarMilisegundos(4000);
        System.out.println("✓ En página del producto: " + driver.getCurrentUrl());
    }


    // ============================================================
    // PASO 4: Seleccionar talla y añadir al carrito
    // ============================================================
    private void paso4_AgregarAlCarrito() throws InterruptedException {
        System.out.println("\n--- PASO 4: Agregamos el producto al carrito ---");

        // Click en "Add to cart"
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@onclick,'addToCart')]")
            )
        ).click();

        // Esperar el alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // (opcional) imprimir mensaje
        System.out.println("Mensaje: " + alert.getText());

        // Aceptar alert
        alert.accept();

        System.out.println("✓ Producto agregado al carrito");


        // Esperamos que el carrito se actualice
        esperarMilisegundos(3000);

        // Cerramos el mini-drawer del carrito si aparece (para ir al carrito completo)
        cerrarPopupsIniciales();
    }


    // ============================================================
    // PASO 5: Ver carrito y proceder al pago
    // ============================================================
    private void paso5_IrAlCarritoYProcederAlPago() throws InterruptedException {
        System.out.println("\n--- PASO 5: Navegando al carrito y procediendo al pago ---");

        // Nos aseguramos de estar en la página principal
        driver.get("https://www.demoblaze.com/index.html");

        esperarMilisegundos(3000);
        // ---- Hacer clic en "Ver carrito" ----
        WebElement botonVerCarrito = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("cartur"))
        );

        if (botonVerCarrito != null) {
            botonVerCarrito.click();
            System.out.println("✓ Clic en Ver carrito");
        }

        // Esperamos que cargue la página del carrito
        esperarMilisegundos(3000);
        cerrarPopupsIniciales();

        System.out.println("✓ En página del carrito: " + driver.getCurrentUrl());

        // 1. Esperar y obtener el botón
        WebElement botonPagar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-target='#orderModal']")
            )
        );

        // 2. Scroll hacia el botón
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});",
            botonPagar
        );

        // (opcional) pequeña pausa si la página es inestable
        esperarMilisegundos(500);

        // 3. Click
        botonPagar.click();
        System.out.println("✓ Clic en Ir a pagar");

        // 4. Validar modal
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("orderModal"))
        );

        
    }

    
    // ============================================================
    // PASO 6: Completar formulario de pago (opcional, no se puede completar sin tarjeta real)
    // ============================================================
    private void paso6_CompletarFormularioDePago() throws InterruptedException {

        System.out.println("\n--- PASO 6: Completando formulario de pago ---");

        System.out.println("Llenando formulario de compra...");

        // Nombre
        WebElement inputNombre = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("name"))
        );
        inputNombre.clear();
        inputNombre.sendKeys("Guillermo Perez");
        System.out.println("✓ Nombre ingresado");

        // País
        WebElement inputPais = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("country"))
        );
        inputPais.clear();
        inputPais.sendKeys("Peru");
        System.out.println("✓ País ingresado");

        // Ciudad
        WebElement inputCiudad = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("city"))
        );
        inputCiudad.clear();
        inputCiudad.sendKeys("Lima");
        System.out.println("✓ Ciudad ingresada");

        // Tarjeta de crédito
        WebElement inputTarjeta = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("card"))
        );
        inputTarjeta.clear();
        inputTarjeta.sendKeys("1234567890123456");
        System.out.println("✓ Tarjeta de crédito ingresada");

        // Mes de expiración
        WebElement inputMes = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("month"))
        );
        inputMes.clear();
        inputMes.sendKeys("12");
        System.out.println("✓ Mes de expiración ingresado");

        // Año de expiración
        WebElement inputAnio = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("year"))
        );
        inputAnio.clear();
        inputAnio.sendKeys("2029");
        System.out.println("✓ Año de expiración ingresado");

        System.out.println("✓ Tarjeta de crédito ingresada");

        esperarMilisegundos(3000);

         // ---- Hacer clic en "comprar" ----
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Purchase']")
            )
        ).click();

        esperarMilisegundos(4000);

        // Esperamos que aparezca el modal de confirmación
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.confirm")
            )
        ).click();
        

        // Esperamos que cargue la página de checkout
        esperarMilisegundos(3000);
        System.out.println("✓ En página de checkout: " + driver.getCurrentUrl());
        System.out.println("\n>>> LA PRUEBA TERMINA AQUÍ (en la página de pago) <<<");
    };


    // ============================================================
    // MÉTODOS DE APOYO (Helpers)
    // Estos métodos son herramientas que usan los pasos anteriores
    // ============================================================

    /**
     * Intenta cerrar banners/popups genéricos que pueden aparecer al cargar la página.
     * No falla si no hay nada que cerrar.
     */
    private void cerrarPopupsIniciales() {
        String[] selectoresCierre = {// Lista de botones comunes para cierre de popups
            "button[aria-label='Close']",
            "button[aria-label='Cerrar']",
            "[data-auto-id='modal-close-btn']",
            ".gl-modal__close",
            "[class*='close-button']",
            "[data-auto-id='cookie-accept-button']",
            "#glass-gdpr-default-consent-accept-button"
        };

        for (String selector : selectoresCierre) {
            try {
                List<WebElement> botones = driver.findElements(By.cssSelector(selector));
                for (WebElement boton : botones) {
                    if (boton.isDisplayed()) {
                        boton.click();
                        esperarMilisegundos(500);
                    }
                }
            } catch (Exception e) {
                // Ignoramos errores aquí; si no hay popup, está bien
            }
        }
    }


    /**
     * Pausa la ejecución por el número de milisegundos indicado.
     * Usamos pausas pequeñas para darle tiempo al navegador de reaccionar.
     *
     * @param milisegundos  Tiempo a esperar (1000 ms = 1 segundo)
     */
    private void esperarMilisegundos(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    // ============================================================
    // LIMPIEZA FINAL (se ejecuta DESPUÉS de todas las pruebas)
    // ============================================================

    /**
     * @AfterSuite: Se ejecuta después de que TODAS las pruebas terminan.
     * Aquí cerramos el navegador para liberar memoria.
     */
    @AfterSuite
    public void cerrarNavegador() {
        System.out.println("\n--- Cerrando navegador ---");
        if (driver != null) {
            driver.quit(); // quit() cierra TODAS las ventanas y termina el proceso
            System.out.println("✓ Navegador cerrado correctamente");
        }
    }
}
