package com.laura.eventpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.laura.eventpro.ui.theme.EventProTheme
import com.laura.eventpro.ui.theme.RoseLight
import java.text.NumberFormat
import java.util.Locale

private data class EventService(val name: String, val description: String, val price: Int, val category: String)
private data class EventReservation(val client: String, val eventType: String, val date: String, val guests: Int, val services: List<String>, val total: Int)

private val sampleServices = listOf(
    EventService("Decoración", "Ambientación y decoración temática", 450000, "Decoración"),
    EventService("Fotografía", "Registro fotográfico del evento", 350000, "Producción"),
    EventService("Catering", "Servicio de alimentos para invitados", 28000, "Alimentación"),
    EventService("Sonido y DJ", "Música, sonido e iluminación básica", 500000, "Producción"),
    EventService("Planeación de boda", "Acompañamiento y coordinación", 900000, "Planeación"),
    EventService("Evento empresarial", "Organización de reuniones corporativas", 750000, "Empresarial"),
    EventService("Mesa de postres", "Estación dulce para los asistentes", 220000, "Alimentación"),
    EventService("Alquiler de mobiliario", "Mesas y sillas para el evento", 300000, "Logística")
)

private fun money(value: Int): String = NumberFormat.getCurrencyInstance(Locale("es", "CO")).format(value)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EventProTheme { EventProApp() } }
    }
}

@Composable
private fun EventProApp() {
    val navController = rememberNavController()
    val selectedServices = remember { mutableStateListOf<String>() }
    val reservations = remember { mutableStateListOf<EventReservation>() }
    var client by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("Cumpleaños") }
    var date by remember { mutableStateOf("") }
    var guestsText by remember { mutableStateOf("30") }
    var message by remember { mutableStateOf("") }
    val guests = guestsText.toIntOrNull()?.coerceAtLeast(0) ?: 0
    val selectedCost = sampleServices.filter { selectedServices.contains(it.name) }.sumOf { it.price }
    val cateringCost = if (selectedServices.contains("Catering")) guests * 28000 else 0
    val total = selectedCost + if (selectedServices.contains("Catering")) cateringCost - 28000 else 0

    Scaffold(
        topBar = { TopAppBarSimple(onHome = { navController.navigate("home") { popUpTo("home") { inclusive = false }; launchSingleTop = true } }) },
        bottomBar = { BottomNavigation(navController) }
    ) { padding ->
        NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(onServices = { navController.navigate("services") }, onReservations = { navController.navigate("reservations") }, reservationCount = reservations.size) }
            composable("services") {
                ServicesScreen(
                    services = sampleServices,
                    selected = selectedServices,
                    onToggle = { name -> if (selectedServices.contains(name)) selectedServices.remove(name) else selectedServices.add(name) },
                    total = total,
                    onBook = { navController.navigate("reservations") }
                )
            }
            composable("reservations") {
                ReservationsScreen(
                    client = client, onClient = { client = it }, eventType = eventType, onEventType = { eventType = it },
                    date = date, onDate = { date = it }, guests = guestsText, onGuests = { guestsText = it },
                    selectedServices = selectedServices.toList(), total = total, reservations = reservations.toList(),
                    message = message,
                    onRegister = {
                        if (client.isBlank() || date.isBlank() || guestsText.toIntOrNull() == null || guests < 1) {
                            message = "Completa el nombre, la fecha y una cantidad válida de invitados."
                        } else if (selectedServices.isEmpty()) {
                            message = "Selecciona al menos un servicio en la pantalla Servicios."
                        } else {
                            reservations.add(EventReservation(client.trim(), eventType, date.trim(), guests, selectedServices.toList(), total))
                            message = "¡Reserva registrada correctamente!"
                            client = ""
                            date = ""
                            guestsText = "30"
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TopAppBarSimple(onHome: () -> Unit) {
    Surface(shadowElevation = 2.dp, color = MaterialTheme.colorScheme.surface) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("EventPro", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Gestión de eventos", style = MaterialTheme.typography.labelMedium)
            }
            TextButton(onClick = onHome) { Text("Inicio") }
        }
    }
}

@Composable
private fun BottomNavigation(navController: NavHostController) {
    NavigationBar(containerColor = RoseLight) {
        NavigationBarItem(selected = false, onClick = { navController.navigate("home") { launchSingleTop = true } }, icon = { Text("⌂") }, label = { Text("Inicio") })
        NavigationBarItem(selected = false, onClick = { navController.navigate("services") { launchSingleTop = true } }, icon = { Text("◇") }, label = { Text("Servicios") })
        NavigationBarItem(selected = false, onClick = { navController.navigate("reservations") { launchSingleTop = true } }, icon = { Text("▣") }, label = { Text("Reservas") })
    }
}

@Composable
private fun HomeScreen(onServices: () -> Unit, onReservations: () -> Unit, reservationCount: Int) {
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Todo evento empieza con una buena idea", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Planea, organiza y cotiza celebraciones y eventos empresariales desde un solo lugar.")
        Card(colors = CardDefaults.cardColors(containerColor = RoseLight), shape = RoundedCornerShape(22.dp)) {
            Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("EVENTOS CON ESTILO", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text("Tu evento, organizado a tu manera", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Bodas · Cumpleaños · Eventos corporativos")
                Button(onClick = onServices, modifier = Modifier.fillMaxWidth()) { Text("Explorar servicios") }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Servicios", "${sampleServices.size}", Modifier.weight(1f))
            StatCard("Reservas creadas", "$reservationCount", Modifier.weight(1f))
        }
        OutlinedButton(onClick = onReservations, modifier = Modifier.fillMaxWidth()) { Text("Crear o consultar reservas") }
        Spacer(Modifier.height(4.dp))
        Text("Nota: los precios son de ejemplo y se pueden ajustar a las tarifas de la empresa.", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ServicesScreen(services: List<EventService>, selected: List<String>, onToggle: (String) -> Unit, total: Int, onBook: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val filtered = services.filter { it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true) }
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text("Nuestros servicios", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Busca y selecciona lo que necesitas para tu evento.")
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Buscar servicio o categoría") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(8.dp))
        Text("${selected.size} servicio(s) seleccionado(s)", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered, key = { it.name }) { service -> ServiceCard(service, selected.contains(service.name), { onToggle(service.name) }) }
        }
        Card(colors = CardDefaults.cardColors(containerColor = RoseLight)) {
            Column(Modifier.fillMaxWidth().padding(12.dp)) {
                Text("Presupuesto estimado: ${money(total)}", fontWeight = FontWeight.Bold)
                Text("El catering se calcula por invitado en la pantalla de reservas.", style = MaterialTheme.typography.bodySmall)
                Button(onClick = onBook, modifier = Modifier.fillMaxWidth()) { Text("Continuar a reserva") }
            }
        }
    }
}

@Composable
private fun ServiceCard(service: EventService, selected: Boolean, onToggle: () -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = if (selected) RoseLight else MaterialTheme.colorScheme.surface)) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(service.name, fontWeight = FontWeight.Bold)
                Text(service.description, style = MaterialTheme.typography.bodySmall)
                Text(if (service.name == "Catering") "${money(service.price)} por invitado" else money(service.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
            Checkbox(checked = selected, onCheckedChange = { onToggle() })
        }
    }
}

@Composable
private fun ReservationsScreen(
    client: String, onClient: (String) -> Unit, eventType: String, onEventType: (String) -> Unit,
    date: String, onDate: (String) -> Unit, guests: String, onGuests: (String) -> Unit,
    selectedServices: List<String>, total: Int, reservations: List<EventReservation>, message: String, onRegister: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text("Nueva reserva", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Ingresa los datos para generar una cotización.")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = client, onValueChange = onClient, label = { Text("Nombre del cliente") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(6.dp))
        Text("Tipo de evento", fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("Boda", "Cumpleaños", "Empresarial").forEach { option ->
                FilterChip(selected = eventType == option, onClick = { onEventType(option) }, label = { Text(option) })
            }
        }
        OutlinedTextField(value = date, onValueChange = onDate, label = { Text("Fecha (DD/MM/AAAA)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(value = guests, onValueChange = onGuests, label = { Text("Cantidad de invitados") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
        Spacer(Modifier.height(6.dp))
        Text("Servicios: ${selectedServices.ifEmpty { listOf("Ninguno seleccionado") }.joinToString()}", style = MaterialTheme.typography.bodySmall)
        Text("Total estimado: ${money(total)}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Button(onClick = onRegister, modifier = Modifier.fillMaxWidth()) { Text("Registrar reserva") }
        if (message.isNotBlank()) { Text(message, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall) }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Text("Reservas de esta sesión (${reservations.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        if (reservations.isEmpty()) {
            Text("Todavía no hay reservas registradas.", style = MaterialTheme.typography.bodySmall)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(reservations.reversed()) { reservation ->
                    Card(colors = CardDefaults.cardColors(containerColor = RoseLight)) {
                        Column(Modifier.fillMaxWidth().padding(10.dp)) {
                            Text(reservation.client, fontWeight = FontWeight.Bold)
                            Text("${reservation.eventType} · ${reservation.date} · ${reservation.guests} invitados")
                            Text("Total: ${money(reservation.total)}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
