# 🛒 Automatización de Pruebas — Flujo de Compra (DemoBlaze)

Proyecto de pruebas automatizadas end-to-end que valida el flujo completo de compra en [DemoBlaze](https://www.demoblaze.com), usando Selenium WebDriver, TestNG y Java.

---

## 📋 Tabla de contenidos

1. [Tecnologías utilizadas](#tecnologías-utilizadas)
2. [Requisitos previos](#requisitos-previos)
3. [Instalación](#instalación)
4. [Ejecución](#ejecución)
5. [Cobertura del flujo](#cobertura-del-flujo)
6. [Pruebas negativas](#pruebas-negativas)
7. [Compatibilidad](#compatibilidad)

---

## 🧰 Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| **Java** | 11+ | Lenguaje base del proyecto |
| **Selenium WebDriver** | 4.18.1 | Automatización del navegador |
| **TestNG** | 7.9.0 | Framework de ejecución y organización de pruebas |
| **WebDriverManager** | 5.7.0 | Descarga y configuración automática del ChromeDriver |
| **Apache Maven** | 3.6+ | Gestión de dependencias y build |
| **Google Chrome** | Última versión estable | Navegador de ejecución |

---

## ✅ Requisitos previos

Antes de instalar o ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- **Java JDK 11 o superior**
  Verificar con: `java -version`

- **Apache Maven 3.6 o superior**
  Verificar con: `mvn -version`

- **Google Chrome** instalado en el sistema
  WebDriverManager descarga automáticamente el ChromeDriver compatible, por lo que **no es necesario instalarlo manualmente**.

- **Conexión a internet** (necesaria para descargar dependencias Maven y para acceder a DemoBlaze durante la ejecución).

---

## 🔧 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/ArturoU5/Calidad.git
cd Calidad
```

### 2. Descargar dependencias

Maven descarga automáticamente todas las dependencias al compilar:

```bash
mvn clean install -DskipTests
```

> Esto descargará Selenium, TestNG y WebDriverManager desde Maven Central.

---

## ▶️ Ejecución

### Ejecutar todas las pruebas

```bash
mvn test
```

Maven tomará automáticamente la configuración de `src/test/resources/testng.xml` y ejecutará la suite completa.

### Ejecutar con salida detallada en consola

```bash
mvn test -Dsurefire.useFile=false
```

### Ejecutar solo la clase de prueba

```bash
mvn test -Dtest=AdidasCompraTest
```

### Ver resultados generados

Después de la ejecución, los reportes se encuentran en:

```
target/surefire-reports/
```

Allí encontrarás archivos `.xml` y `.txt` con el resumen de cada prueba ejecutada.

---

## 🗺️ Cobertura del flujo

El test `flujoCompletoCompraAdidas` cubre el siguiente flujo E2E de 6 pasos:

```
[Inicio] → Abrir DemoBlaze → Login → Seleccionar Producto
        → Agregar al Carrito → Ver Carrito → Formulario de Pago → [Confirmación]
```

### Detalle por paso

| Paso | Método | Descripción | Verificación |
|---|---|---|---|
| **1** | `paso1_AbrirPaginaPrincipal()` | Navega a `demoblaze.com` | Título de la página contiene `"STORE"` |
| **2** | `paso2_IniciarSesion()` | Abre el modal de login, ingresa usuario y contraseña, hace clic en "Log in" | Visibilidad del modal `#logInModal` |
| **3** | `paso3_SeleccionarProducto()` | Selecciona el primer producto visible en la página principal | Verifica URL del producto en consola |
| **4** | `paso4_AgregarAlCarrito()` | Hace clic en "Add to cart" y acepta el alert de confirmación | Alert presente y aceptado |
| **5** | `paso5_IrAlCarritoYProcederAlPago()` | Navega al carrito, hace scroll hasta "Place Order" y abre el modal de pago | Visibilidad del modal `#orderModal` |
| **6** | `paso6_CompletarFormularioDePago()` | Llena nombre, país, ciudad, tarjeta, mes y año; hace clic en "Purchase" y confirma | Aparición del botón `.confirm` en el modal de éxito |

### Datos de prueba utilizados

| Campo | Valor |
|---|---|
| Usuario | `seletest` |
| Contraseña | `bazm2$` |
| Nombre | Guillermo Perez |
| País | Peru |
| Ciudad | Lima |
| Tarjeta | 1234567890123456 |
| Mes de expiración | 12 |
| Año de expiración | 2029 |

> ⚠️ Las credenciales son de una cuenta de prueba en DemoBlaze. No usar datos reales.

---

## ❌ Pruebas negativas

A continuación se documentan los escenarios negativos que **deberían considerarse** para una cobertura robusta. Actualmente el proyecto cubre el camino feliz (happy path); las siguientes pruebas representan casos de fallo esperados:

### Autenticación

| Escenario | Entrada | Resultado esperado |
|---|---|---|
| Login con credenciales incorrectas | Usuario o contraseña erróneos | Alert con mensaje de error; no se redirige |
| Login con campos vacíos | Usuario y contraseña en blanco | Alert indicando campos requeridos |
| Login con solo el usuario | Contraseña vacía | Alert de error |

### Carrito

| Escenario | Entrada | Resultado esperado |
|---|---|---|
| Proceder al pago con carrito vacío | Sin productos añadidos | Botón "Place Order" no disponible o total en $0 |

### Formulario de pago

| Escenario | Entrada | Resultado esperado |
|---|---|---|
| Enviar formulario vacío | Todos los campos en blanco | Alert indicando campos requeridos |
| Número de tarjeta con letras | `ABCD1234EFGH5678` | Error de validación o compra rechazada |
| Año de expiración pasado | `2020` | Error de validación de fecha |
| Mes fuera de rango | `13` o `00` | Error de validación |
| Nombre con caracteres especiales | `<script>alert(1)</script>` | El campo no debe ejecutar el script (XSS) |

### Navegación

| Escenario | Descripción | Resultado esperado |
|---|---|---|
| Sin conexión a internet | Cortar red durante la prueba | Timeout controlado; la prueba falla con mensaje claro |
| Producto no disponible en la tienda | Todos los productos eliminados del catálogo | Mensaje de "No hay productos" visible |

---

## 🖥️ Compatibilidad

### Sistema operativo

| Sistema Operativo | Compatible |
|---|---|
| Windows 10 / 11 | ✅ |
| macOS 12+ (Monterey o superior) | ✅ |
| Ubuntu 20.04 / 22.04 | ✅ |
| Otras distribuciones Linux con GUI | ✅ (requiere Chrome instalado) |

> ⚠️ El proyecto usa ChromeDriver (headful por defecto). En servidores sin interfaz gráfica es necesario agregar el argumento `--headless` en las `ChromeOptions` del método `configurarNavegador()`.

### Navegador

| Navegador | Compatible |
|---|---|
| Google Chrome (última versión) | ✅ |
| Chromium | ✅ (ajuste manual del driver) |
| Firefox, Edge, Safari | ❌ (no configurados) |

### Java

| Versión de Java | Compatible |
|---|---|
| Java 11 | ✅ (versión mínima requerida) |
| Java 17 (LTS) | ✅ |
| Java 21 (LTS) | ✅ |
| Java 8 o inferior | ❌ |

### Maven

| Versión de Maven | Compatible |
|---|---|
| Maven 3.6.x | ✅ |
| Maven 3.8.x | ✅ |
| Maven 3.9.x | ✅ |
| Maven 2.x | ❌ |

---

## 📁 Estructura del proyecto

```
Calidad/
├── pom.xml                                          # Configuración Maven y dependencias
└── src/
    └── test/
        ├── java/
        │   └── com/adidas/tests/
        │       └── AdidasCompraTest.java            # Clase principal de pruebas
        └── resources/
            └── testng.xml                           # Suite de TestNG
```

---

## 📝 Notas adicionales

- El proyecto utiliza **esperas explícitas** (`WebDriverWait`) con un timeout máximo de 20 segundos por elemento, lo que lo hace tolerante a variaciones en el tiempo de carga de la página.
- El método `cerrarPopupsIniciales()` maneja automáticamente banners de cookies y modales emergentes comunes, evitando fallos espurios.
- WebDriverManager gestiona la descarga del ChromeDriver de forma automática; no es necesario mantener el driver actualizado manualmente.
