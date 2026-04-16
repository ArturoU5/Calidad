# 🛒 Proyecto de Automatización - Adidas PE con Selenium WebDriver

## 📋 ¿Qué hace este proyecto?

Este proyecto automatiza el siguiente flujo en **adidas.pe** usando Java y Selenium:

1. ✅ Abre la página principal de Adidas Perú
2. ✅ Hace clic en el ícono de iniciar sesión
3. ✅ Ingresa el correo `arturougaz514@gmail.com`
4. ✅ Da clic en "Continuar"
5. ✅ Ingresa la contraseña
6. ✅ Cierra popups y rechaza solicitudes de clave de acceso (passkey)
7. ✅ Navega a la página principal
8. ✅ Selecciona un producto
9. ✅ Selecciona una talla disponible
10. ✅ Añade el producto al carrito
11. ✅ Hace clic en "Ver carrito"
12. ✅ Hace clic en "Ir a pagar" ← **FIN DE LA PRUEBA**

---

## 🖥️ Requisitos previos (lo que necesitas instalar)

Antes de ejecutar el proyecto, necesitas tener instalado:

### 1️⃣ Java Development Kit (JDK) 11 o superior

**¿Qué es?** El lenguaje en el que está escrito este proyecto.

**Cómo instalarlo:**
1. Ve a: https://adoptium.net/es/temurin/releases/
2. Descarga la versión **JDK 11** para tu sistema operativo
3. Instálalo con las opciones por defecto
4. Para verificar que quedó bien: abre una terminal y escribe:
   ```
   java -version
   ```
   Deberías ver algo como: `openjdk version "11.0.xx"`

---

### 2️⃣ Apache Maven

**¿Qué es?** Una herramienta que descarga automáticamente todas las librerías que el proyecto necesita (Selenium, ChromeDriver, etc.).

**Cómo instalarlo en Windows:**
1. Ve a: https://maven.apache.org/download.cgi
2. Descarga el archivo `.zip` (Binary zip archive)
3. Descomprime la carpeta en `C:\Program Files\Maven`
4. Agrega Maven al PATH del sistema:
   - Busca "Variables de entorno" en el menú de inicio
   - En "Variables del sistema" busca `Path` → clic en Editar
   - Agrega una nueva línea: `C:\Program Files\Maven\bin`
5. Abre una nueva terminal y verifica:
   ```
   mvn -version
   ```
   Deberías ver algo como: `Apache Maven 3.x.x`

**Cómo instalarlo en Mac:**
```bash
brew install maven
```

---

### 3️⃣ Google Chrome

**¿Qué es?** El navegador que Selenium va a controlar.

> ⚠️ **IMPORTANTE**: WebDriverManager (incluido en el proyecto) descarga automáticamente el ChromeDriver compatible con tu versión de Chrome. ¡No necesitas descargarlo manualmente!

Verifica que tienes Chrome instalado. Si no:
- Descárgalo desde: https://www.google.com/chrome/

---

### 4️⃣ Visual Studio Code (VSC)

**¿Qué es?** El editor de código donde vas a abrir y ejecutar el proyecto.

1. Descarga desde: https://code.visualstudio.com/
2. Instálalo con las opciones por defecto

**Extensiones necesarias para VSC:**
Una vez abierto VSC, instala estas extensiones:
- Presiona `Ctrl+Shift+X` (o `Cmd+Shift+X` en Mac)
- Busca e instala:
  - **"Extension Pack for Java"** (de Microsoft) - incluye todo lo necesario
  - **"Maven for Java"** (de Microsoft)

---

## 📁 Estructura del proyecto

```
adidas-selenium-test/
│
├── pom.xml                          ← Configuración de Maven (librerías)
│
└── src/
    └── test/
        ├── java/
        │   └── com/adidas/tests/
        │       └── AdidasCompraTest.java    ← EL CÓDIGO PRINCIPAL
        │
        └── resources/
            └── testng.xml           ← Configuración de la suite de pruebas
```

---

## 🚀 Cómo ejecutar el proyecto paso a paso

### Opción A: Desde Visual Studio Code (recomendado para principiantes)

**Paso 1: Abrir el proyecto**
1. Abre VSC
2. Ve a `Archivo` → `Abrir Carpeta`
3. Selecciona la carpeta `adidas-selenium-test`
4. VSC detectará automáticamente que es un proyecto Maven y descargará las dependencias (puede tardar 2-5 minutos la primera vez)

**Paso 2: Esperar que cargue**
- En la esquina inferior izquierda verás una barra de progreso
- Espera a que desaparezca y los archivos `.java` dejen de tener errores

**Paso 3: Ejecutar la prueba**

**Opción 3A - Desde el archivo Java:**
1. Abre `AdidasCompraTest.java`
2. Busca el método `flujoCompletoCompraAdidas()`
3. Verás un ícono ▶️ verde a la izquierda del método
4. Haz clic en él → selecciona "Run Test"

**Opción 3B - Desde la terminal de VSC:**
1. Abre la terminal: `Ver` → `Terminal` (o `Ctrl+` `` ` ``)
2. Escribe este comando:
   ```bash
   mvn test
   ```
3. Presiona Enter y observa la ejecución

---

### Opción B: Desde la terminal (CMD/PowerShell en Windows, Terminal en Mac)

1. Navega a la carpeta del proyecto:
   ```bash
   cd ruta/a/adidas-selenium-test
   ```
   Ejemplo en Windows: `cd C:\Users\TuNombre\Desktop\adidas-selenium-test`

2. Ejecuta las pruebas:
   ```bash
   mvn test
   ```

---

## 👀 ¿Qué verás cuando se ejecute?

1. **Se abre una ventana de Chrome** (esto es normal, es Selenium controlando el navegador)
2. En la terminal/consola verás mensajes como:
   ```
   ========================================
     Iniciando configuración del navegador
   ========================================
   ✓ Navegador iniciado correctamente

   --- PASO 1: Abriendo Adidas PE ---
   ✓ Página principal cargada: adidas

   --- PASO 2: Iniciando sesión ---
   ✓ Email ingresado
   ✓ Contraseña ingresada
   ✓ Login completado

   --- PASO 3: Cerrando popups ---
   ✓ Popups cerrados, en página principal

   ... (continúa)

   ========================================
     ✓ PRUEBA COMPLETADA EXITOSAMENTE
   ========================================
   ```
3. Al final, Chrome se cierra automáticamente

---

## ❗ Problemas comunes y cómo solucionarlos

### Error: "No se puede encontrar el elemento" o TimeoutException
**Causa:** Adidas cambió su sitio web y los selectores CSS ya no funcionan.
**Solución:** 
1. Abre adidas.pe manualmente
2. Presiona `F12` para abrir las DevTools
3. Usa el inspector para encontrar el nuevo selector del elemento
4. Actualiza el selector correspondiente en `AdidasCompraTest.java`

### Error: "ChromeDriver no encontrado" o "session not created"
**Causa:** Incompatibilidad entre Chrome y ChromeDriver.
**Solución:** WebDriverManager debería manejarlo automáticamente. Si persiste:
1. Actualiza Chrome a la última versión
2. Si el error persiste, agrega esto a `pom.xml` con la versión correcta de WebDriverManager

### Error: "BUILD FAILURE" con errores de compilación
**Causa:** Java o Maven no están configurados correctamente.
**Solución:** Verifica que `java -version` y `mvn -version` funcionen en la terminal

### La prueba falla en el login
**Causa:** Las credenciales pueden haber expirado o Adidas puede tener verificación extra.
**Solución:** Verifica manualmente que el correo y contraseña funcionan en adidas.pe

### Error: "StaleElementReferenceException"
**Causa:** La página se recargó y el elemento que teníamos ya no existe.
**Solución:** El código ya maneja esto, pero si ocurre, intenta aumentar los tiempos de espera en `esperarMilisegundos()`

---

## 🔧 Conceptos clave de Selenium (para entender el código)

| Concepto | Explicación |
|----------|-------------|
| `WebDriver` | El "robot" que controla el navegador |
| `ChromeDriver` | La versión de WebDriver para Chrome |
| `WebDriverWait` | Espera inteligente: espera hasta que algo ocurra |
| `By.cssSelector()` | Busca elementos por su clase CSS o atributo |
| `By.xpath()` | Busca elementos por su estructura en el HTML |
| `findElement()` | Encuentra UN elemento (falla si no existe) |
| `findElements()` | Encuentra TODOS los elementos (lista vacía si no hay) |
| `.click()` | Hace clic en un elemento |
| `.sendKeys()` | Escribe texto en un campo |
| `.getText()` | Obtiene el texto visible de un elemento |
| `JavascriptExecutor` | Ejecuta código JavaScript directamente en el navegador |

---

## 📞 ¿Necesitas ayuda?

Si el proyecto falla, revisa:
1. Que Java 11+ esté instalado (`java -version`)
2. Que Maven esté instalado (`mvn -version`)  
3. Que tengas conexión a internet (Maven descarga librerías)
4. Que Chrome esté instalado y actualizado
5. Los mensajes de error en la consola (generalmente indican exactamente el problema)
