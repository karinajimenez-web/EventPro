# Guion breve para el video de EventPro

Hola, mi nombre es Laura y voy a presentar EventPro, una aplicación móvil propuesta para apoyar la gestión de una empresa de organización de eventos. Su objetivo es facilitar la consulta de servicios, la estimación de presupuestos y el registro de solicitudes de reserva.

Primero, en la pantalla de inicio encontramos el nombre de la aplicación, una breve descripción y accesos a los módulos principales.

En la pantalla Servicios se muestran diferentes opciones, como decoración, fotografía, catering, sonido y planeación. Los servicios se generan desde una colección de datos y se presentan en una lista dinámica. También tenemos un buscador para filtrar los servicios por nombre o categoría. Podemos seleccionar y deseleccionar los servicios que necesita el cliente.

La aplicación calcula un presupuesto estimado según los servicios seleccionados y tiene en cuenta la cantidad de invitados cuando se incluye catering. Estos precios son de demostración y se pueden ajustar.

En la pantalla Reservas ingresamos el nombre del cliente, el tipo de evento, la fecha y el número de invitados. Al registrar, la aplicación valida los campos y muestra la reserva en la lista de esta sesión. Así se observa una interacción real y un cambio visible en la interfaz.

Para desarrollar la aplicación utilicé Kotlin, Jetpack Compose y Material 3. La navegación entre Inicio, Servicios y Reservas se maneja con Navigation Compose, mediante NavController y NavHost. El estado y los eventos permiten actualizar la búsqueda, la selección de servicios, el presupuesto y las reservas.

Esta es la primera versión académica de EventPro. Gracias.
