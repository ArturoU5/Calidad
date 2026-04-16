package com.adidas.tests;

// ============================================================
// IMPORTACIONES: Le decimos a Java qué librerías vamos a usar
// ============================================================
import java.time.Duration;
import java.util.List;

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
 * PRUEBA AUTOMATIZADA: Flujo de compra en Adidas Perú
 * =======================================================================
 *
 * Esta clase automatiza el siguiente flujo:
 *   1. Abrir la página principal de Adidas PE
 *   2. Iniciar sesión con credenciales
 *   3. Cerrar popups y solicitudes de clave de acceso
 *   4. Seleccionar un producto
 *   5. Elegir talla y añadir al carrito
 *   6. Ir al carrito y proceder al pago
 *
 * CONCEPTOS CLAVE QUE VERÁS EN ESTE CÓDIGO:
 *   - WebDriver: el "robot" que controla el navegador
 *   - WebDriverWait: le dice al robot que espere antes de hacer algo
 *   - By: la forma de "encontrar" elementos en la página (botones, inputs, etc.)
 *   - findElement: busca UN elemento en la página
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
    private static final String EMAIL    = "arturougaz514@gmail.com";
    private static final String PASSWORD = "At74842024@";


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
        // para tu versión de Chrome. ¡Sin esto tendrías que hacerlo a mano!
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
        paso3_CerrarPopupsYClaveAcceso();
        paso4_SeleccionarProducto();
        paso5_SeleccionarTallaYAgregarAlCarrito();
        paso6_IrAlCarritoYProcederAlPago();

        System.out.println("\n========================================");
        System.out.println("  ✓ PRUEBA COMPLETADA EXITOSAMENTE      ");
        System.out.println("========================================");
    }


    // ============================================================
    // PASO 1: Abrir la página principal de Adidas PE
    // ============================================================
    private void paso1_AbrirPaginaPrincipal() {
        System.out.println("\n--- PASO 1: Abriendo Adidas PE ---");

        // driver.get() navega a una URL, igual que escribirla en la barra del navegador
        driver.get("https://www.adidas.pe/");

        // Esperamos hasta que el título de la página contenga "adidas"
        // Esto confirma que la página cargó correctamente
        wait.until(ExpectedConditions.titleContains("adidas"));

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

        // ---- Hacer clic en el ícono de inicio de sesión ----
        // Buscamos el botón/ícono de cuenta de usuario
        // Probamos diferentes selectores porque el sitio puede usar distintos elementos
        WebElement iconoCuenta = encontrarElemento(
            By.cssSelector("[data-auto-id='glass-header-desktop-link-account']"),
            By.cssSelector("[data-auto-id='customer-info-button']"),  // New selector from HTML
            By.cssSelector("[class*='account']"),
            By.cssSelector("._user_icon_xw3y3_1"),  // Class from HTML
            By.xpath("//a[contains(@href,'login') or contains(@data-auto-id,'account')]"),
            By.xpath("//*[svg[@viewBox='0 0 32 32']]"),  // Element containing the user icon SVG
            By.xpath("//a[@href*='login']"),  // Direct link to login
            By.xpath("//button[@aria-label='Account' or @aria-label='Login' or @aria-label='Iniciar sesión' or @aria-label='Regístrate o inicia sesión en adiClub ']")  // Button with aria-label
        );

        if (iconoCuenta != null) {
            iconoCuenta.click();
            System.out.println("✓ Clic en ícono de cuenta");
        } else {
            // Si no encontramos el ícono, navegamos directamente a login
            System.out.println("! Ícono no encontrado, navegando directamente a login...");
            driver.get("https://www.adidas.pe/login");
        }

        esperarMilisegundos(2000);

        // ---- Ingresar el email ----
        WebElement campoEmail = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='email'], input[name='email'], #email, input[id*='email'], #username")
            )
        );
        campoEmail.clear();                    // Limpiamos el campo por si tiene texto previo
        campoEmail.sendKeys(EMAIL);            // Escribimos el email
        System.out.println("✓ Email ingresado");

        esperarMilisegundos(3000);  // Increased wait after entering email

        // ---- Aceptar checkbox de términos y condiciones ----
        esperarMilisegundos(1000);  // Wait for checkbox to appear
        try {
            WebElement checkbox = driver.findElement(By.name("doc-account-tnc-pp:adidas:pe:202239"));
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println("✓ Checkbox de términos aceptado");
            }
        } catch (Exception e) {
            System.out.println("! Checkbox no encontrado o ya aceptado");
        }
        esperarMilisegundos(1000);  // Wait after accepting checkbox

        // ---- Clic en "Continuar" ----
        esperarMilisegundos(2000);  // Additional wait before finding continue button
        WebElement botonContinuar = encontrarElemento(
            By.cssSelector("button[type='submit']"),
            By.xpath("//button[contains(text(),'Continuar') or contains(text(),'Continue')]"),
            By.cssSelector("[data-auto-id='login-email-btn']")
        );

        if (botonContinuar != null) {
            botonContinuar.click();
            System.out.println("✓ Clic en Continuar");
        }

        esperarMilisegundos(5000);  // Increased wait after clicking continue

        // Check for error messages
        try {
            WebElement errorMsg = driver.findElement(By.xpath("//*[contains(text(),'Ocurrió un error') or contains(text(),'error') or contains(text(),'Error')]"));
            if (errorMsg.isDisplayed()) {
                System.out.println("❌ Error encontrado después de Continuar: " + errorMsg.getText());
                throw new RuntimeException("Error en login: " + errorMsg.getText());
            }
        } catch (Exception e) {
            // No error found, continue
        }

        // ---- Ingresar la contraseña ----

        // ---- Ingresar la contraseña ----
        WebElement campoPassword = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='password'], input[name='password'], #password")
            )
        );
        campoPassword.clear();
        campoPassword.sendKeys(PASSWORD);
        System.out.println("✓ Contraseña ingresada");

        esperarMilisegundos(500);

        // ---- Clic en "Iniciar sesión" / "Login" ----
        WebElement botonLogin = encontrarElemento(
            By.cssSelector("button[type='submit']"),
            By.xpath("//button[contains(text(),'Iniciar') or contains(text(),'Entrar') or contains(text(),'Login') or contains(text(),'Sign in')]"),
            By.cssSelector("[data-auto-id='login-submit']")
        );

        if (botonLogin != null) {
            botonLogin.click();
            System.out.println("✓ Clic en Iniciar Sesión");
        }

        // Esperamos que la página cargue después del login
        esperarMilisegundos(4000);
        System.out.println("✓ Login completado");
    }


    // ============================================================
    // PASO 3: Cerrar popups y rechazar solicitud de clave de acceso
    // ============================================================
    private void paso3_CerrarPopupsYClaveAcceso() throws InterruptedException {
        System.out.println("\n--- PASO 3: Cerrando popups ---");

        // Intentamos varias veces cerrar diferentes tipos de popups
        // porque pueden aparecer en cualquier orden
        for (int intento = 0; intento < 5; intento++) {
            boolean cerradoAlgo = false;

            // ---- Rechazar solicitud de clave de acceso (Passkey) ----
            // Adidas a veces pide crear una "llave de acceso" (passkey biométrica)
            WebElement botonRechazarPasskey = encontrarElementoRapido(
                By.xpath("//button[contains(text(),'Saltar') or contains(text(),'Skip') or contains(text(),'Not now') or contains(text(),'Ahora no')]"),
                By.xpath("//button[contains(text(),'Rechazar') or contains(text(),'Decline')]"),
                By.cssSelector("[data-auto-id='passkey-skip'], [data-auto-id='passkey-decline']")
            );

            if (botonRechazarPasskey != null) {
                botonRechazarPasskey.click();
                System.out.println("✓ Solicitud de passkey rechazada");
                cerradoAlgo = true;
                esperarMilisegundos(1500);
            }

            // ---- Cerrar popups genéricos (botones X de cierre) ----
            List<WebElement> botonesClose = driver.findElements(
                By.cssSelector("button[aria-label='Close'], button[aria-label='Cerrar'], [data-auto-id='modal-close-btn'], .gl-modal__close")
            );

            for (WebElement boton : botonesClose) {
                try {
                    if (boton.isDisplayed() && boton.isEnabled()) {
                        boton.click();
                        System.out.println("✓ Popup cerrado");
                        cerradoAlgo = true;
                        esperarMilisegundos(1000);
                    }
                } catch (Exception e) {
                    // Si no se puede hacer clic, ignoramos y seguimos
                }
            }

            // ---- Cerrar banner de cookies (si aparece) ----
            WebElement botonCookies = encontrarElementoRapido(
                By.cssSelector("[data-auto-id='cookie-accept-button']"),
                By.xpath("//button[contains(text(),'Aceptar') and contains(@class,'cookie')]")
            );

            if (botonCookies != null) {
                botonCookies.click();
                System.out.println("✓ Banner de cookies cerrado");
                cerradoAlgo = true;
                esperarMilisegundos(1000);
            }

            // Si en este intento no cerramos nada, salimos del bucle
            if (!cerradoAlgo) break;
        }

        System.out.println("✓ Popups cerrados, en página principal");
        esperarMilisegundos(2000);
    }


    // ============================================================
    // PASO 4: Seleccionar un producto
    // ============================================================
    private void paso4_SeleccionarProducto() throws InterruptedException {
        System.out.println("\n--- PASO 4: Seleccionando producto ---");

        // Nos aseguramos de estar en la página principal
        driver.get("https://www.adidas.pe/");
        esperarMilisegundos(3000);

        // Cerrar cualquier popup que aparezca al recargar
        cerrarPopupsIniciales();

        // Buscamos cualquier producto en la página
        // Los productos en Adidas generalmente están en elementos <article> o tarjetas con clase "product"
        List<WebElement> productos = driver.findElements(
            By.cssSelector("article.product-card, [data-auto-id='product-card'], .product-card, [class*='ProductCard']")
        );

        if (productos.isEmpty()) {
            // Si no encontramos productos en la home, navegamos a una categoría
            System.out.println("! No hay productos visibles en home, buscando en categoría...");
            driver.get("https://www.adidas.pe/zapatillas");
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
        esperarMilisegundos(3000);
        System.out.println("✓ En página del producto: " + driver.getCurrentUrl());
    }


    // ============================================================
    // PASO 5: Seleccionar talla y añadir al carrito
    // ============================================================
    private void paso5_SeleccionarTallaYAgregarAlCarrito() throws InterruptedException {
        System.out.println("\n--- PASO 5: Seleccionando talla y añadiendo al carrito ---");

        // ---- Seleccionar talla ----
        // Las tallas en Adidas suelen estar en botones dentro de una sección de tallas
        List<WebElement> botonesTalla = driver.findElements(
            By.cssSelector(
                "[data-auto-id='size-selector-button']:not([disabled]), " +
                "button[class*='size']:not([disabled]), " +
                "[class*='size-selector'] button:not([disabled]), " +
                "[data-auto-id='size-button']:not([disabled])"
            )
        );

        if (!botonesTalla.isEmpty()) {
            // Buscamos la primera talla disponible (no deshabilitada)
            WebElement tallaDisponible = null;

            for (WebElement boton : botonesTalla) {
                String clases = boton.getAttribute("class") != null ? boton.getAttribute("class") : "";
                String ariaDisabled = boton.getAttribute("aria-disabled") != null ? boton.getAttribute("aria-disabled") : "";

                // Verificamos que no esté marcada como agotada
                if (!clases.contains("disabled") && !clases.contains("sold-out") && !ariaDisabled.equals("true")) {
                    tallaDisponible = boton;
                    break;
                }
            }

            if (tallaDisponible != null) {
                // Scrolleamos hasta la talla
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                    tallaDisponible
                );
                esperarMilisegundos(500);
                tallaDisponible.click();
                System.out.println("✓ Talla seleccionada: " + tallaDisponible.getText());
            } else {
                System.out.println("! No se encontró talla disponible, intentando el primer botón...");
                botonesTalla.get(0).click();
            }
        } else {
            System.out.println("! No se encontraron botones de talla, el producto puede no requerirla");
        }

        esperarMilisegundos(1000);

        // ---- Añadir al carrito ----
        WebElement botonAgregarCarrito = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "[data-auto-id='add-to-cart-button'], " +
                    "button[class*='add-to-cart'], " +
                    "[class*='AddToCart'] button, " +
                    "button[id*='add-to-cart']"
                )
            )
        );

        // Scrolleamos hasta el botón
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
            botonAgregarCarrito
        );
        esperarMilisegundos(500);

        botonAgregarCarrito.click();
        System.out.println("✓ Producto añadido al carrito");

        // Esperamos que el carrito se actualice
        esperarMilisegundos(3000);

        // Cerramos el mini-drawer del carrito si aparece (para ir al carrito completo)
        cerrarPopupsIniciales();
    }


    // ============================================================
    // PASO 6: Ver carrito y proceder al pago
    // ============================================================
    private void paso6_IrAlCarritoYProcederAlPago() throws InterruptedException {
        System.out.println("\n--- PASO 6: Navegando al carrito y procediendo al pago ---");

        // ---- Hacer clic en "Ver carrito" ----
        WebElement botonVerCarrito = encontrarElemento(
            By.cssSelector("[data-auto-id='view-cart-button']"),
            By.xpath("//button[contains(text(),'Ver carrito') or contains(text(),'View cart') or contains(text(),'Ver bag')]"),
            By.cssSelector("a[href='/es/cart'], a[href='/cart'], [class*='cart-link']")
        );

        if (botonVerCarrito != null) {
            botonVerCarrito.click();
            System.out.println("✓ Clic en Ver carrito");
        } else {
            // Si no encontramos el botón, navegamos directamente al carrito
            System.out.println("! Navegando directamente al carrito...");
            driver.get("https://www.adidas.pe/cart");
        }

        // Esperamos que cargue la página del carrito
        esperarMilisegundos(3000);
        cerrarPopupsIniciales();

        System.out.println("✓ En página del carrito: " + driver.getCurrentUrl());

        // ---- Hacer clic en "Ir a pagar" / "Checkout" ----
        WebElement botonPagar = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "[data-auto-id='checkout-button'], " +
                    "[data-auto-id='proceed-to-checkout'], " +
                    "button[class*='checkout'], " +
                    "a[class*='checkout']"
                )
            )
        );

        // Scrolleamos hasta el botón de pago
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
            botonPagar
        );
        esperarMilisegundos(500);

        botonPagar.click();
        System.out.println("✓ Clic en Ir a pagar");

        // Esperamos que cargue la página de checkout
        esperarMilisegundos(3000);
        System.out.println("✓ En página de checkout: " + driver.getCurrentUrl());
        System.out.println("\n>>> LA PRUEBA TERMINA AQUÍ (en la página de pago) <<<");
    }


    // ============================================================
    // MÉTODOS DE APOYO (Helpers)
    // Estos métodos son herramientas que usan los pasos anteriores
    // ============================================================

    /**
     * Intenta cerrar banners/popups genéricos que pueden aparecer al cargar la página.
     * No falla si no hay nada que cerrar.
     */
    private void cerrarPopupsIniciales() {
        String[] selectoresCierre = {
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
     * Intenta encontrar un elemento usando múltiples selectores.
     * Espera hasta TIEMPO_ESPERA segundos para cada selector.
     * Devuelve el primer elemento que encuentre, o null si no encuentra ninguno.
     *
     * @param selectores  Uno o más objetos By (CSS, XPath, etc.)
     * @return El WebElement encontrado, o null
     */
    private WebElement encontrarElemento(By... selectores) {
        for (By selector : selectores) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(selector));
            } catch (Exception e) {
                // Este selector no funcionó, intentamos el siguiente
            }
        }
        return null; // Ningún selector funcionó
    }

    /**
     * Como encontrarElemento() pero con espera más corta (3 segundos).
     * Útil para elementos opcionales como popups que puede que no aparezcan.
     *
     * @param selectores  Uno o más objetos By
     * @return El WebElement encontrado, o null
     */
    private WebElement encontrarElementoRapido(By... selectores) {
        WebDriverWait waitCorto = new WebDriverWait(driver, Duration.ofSeconds(3));
        for (By selector : selectores) {
            try {
                return waitCorto.until(ExpectedConditions.elementToBeClickable(selector));
            } catch (Exception e) {
                // No encontrado con este selector, seguimos
            }
        }
        return null;
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
