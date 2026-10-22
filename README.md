# EventPro — Gestión de eventos

Aplicación móvil académica desarrollada en Kotlin y Jetpack Compose para una empresa de organización de eventos. Permite consultar servicios, filtrar por nombre/categoría, seleccionar servicios, estimar un presupuesto y registrar reservas localmente durante la sesión.

## Objetivo
Centralizar en una interfaz móvil sencilla la selección de servicios y la creación de una cotización preliminar para bodas, cumpleaños y eventos empresariales.

## Pantallas
1. **Inicio:** identidad visual, resumen del sistema y accesos principales.
2. **Servicios:** catálogo dinámico de ocho servicios, búsqueda y selección.
3. **Reservas:** formulario de cliente, tipo y fecha del evento, cantidad de invitados, presupuesto y listado de reservas creadas.

## Funcionalidades interactivas
- **Trabajada en clase:** búsqueda y filtrado de servicios.
- **Nueva 1:** formulario con validaciones para registrar una reserva y mostrarla en la lista.
- **Nueva 2:** cálculo del presupuesto a partir de los servicios seleccionados y cantidad de invitados para catering.

## Tecnologías y conceptos
- Kotlin, Jetpack Compose y Material 3.
- Navigation Compose (`NavController` y `NavHost`).
- `Column`, `Row`, `Card`, `Button`, `OutlinedTextField`, `LazyColumn` y componentes `@Composable` reutilizables.
- Estado con `remember` y `mutableStateOf` / `mutableStateListOf`.
- Tema visual personalizado en `Color.kt`, `Theme.kt` y `Type.kt`.

## Ejecutar
1. Instala Android Studio con Android SDK y JDK 17.
2. Descomprime el proyecto y abre la carpeta `EventPro` en Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Ejecuta en un emulador o dispositivo Android (API 24 o superior).

## Alcance y limitaciones
Los servicios, tarifas y reservas son datos de demostración. No hay base de datos ni persistencia: las reservas permanecen en memoria mientras la aplicación está abierta y se borran al cerrarla. Los precios son ilustrativos y deben ajustarse a las tarifas reales de la empresa.

## Video demostrativo (guion sugerido de 3–5 minutos)
1. Presentar EventPro y su propósito (20–30 s).
2. Mostrar la identidad visual y pantalla Inicio (30 s).
3. Abrir Servicios, buscar “foto”, seleccionar servicios y explicar la lista dinámica (60 s).
4. Mostrar el presupuesto estimado y cambiar selecciones para demostrar que cambia (40 s).
5. Abrir Reservas, ingresar datos, registrar una reserva y mostrarla en la lista (60–90 s).
6. Cerrar explicando que Navigation Compose gestiona las pantallas y que el estado actualiza la interfaz (20–30 s).

## Entrega
Publica el código en un repositorio público de GitHub y agrega el enlace del repositorio y el enlace del video a Google Classroom. Antes de entregar, ejecuta y prueba la app en Android Studio.
