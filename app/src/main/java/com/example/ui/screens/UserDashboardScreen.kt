package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.formatCurrency

@Composable
fun UserDashboardScreen(
    userListings: List<Listing>,
    savedListings: List<Listing>,
    invoices: List<Invoice>,
    messages: List<Message>,
    onSelectListing: (Listing) -> Unit,
    onSelectInvoice: (Invoice) -> Unit,
    onNavigateAnalytics: () -> Unit,
    onNavigateSettings: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Mis Publicaciones, 1: Favoritos, 2: Mensajes, 3: Facturas
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("¡Bienvenido a SURGIR!", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("Gestión Unificada de Tu Ecosistema Digital", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                }
            }
        }

        // Quick Action Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onNavigateAnalytics,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Estadísticas", fontSize = 11.sp)
            }

            OutlinedButton(
                onClick = onNavigateSettings,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Ajustes", fontSize = 11.sp)
            }
        }

        // Segmented Tab Row
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Actividad (${userListings.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Favoritos (${savedListings.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                Text("Mensajes (${messages.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 12.dp))
            }
            Tab(selected = selectedTab == 3, onClick = { selectedTab = 3 }) {
                Text("Facturas (${invoices.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        when (selectedTab) {
            0 -> {
                Text("Tus Publicaciones y Servicios Activos", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (userListings.isEmpty()) {
                    Text("Aún no tienes publicaciones.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    userListings.forEach { listing ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            onClick = { onSelectListing(listing) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(listing.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("${listing.category} • ${formatCurrency(listing.price)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                }
                                Icon(Icons.Default.ChevronRight, contentDescription = null)
                            }
                        }
                    }
                }
            }
            1 -> {
                Text("Publicaciones Guardadas", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (savedListings.isEmpty()) {
                    Text("No has guardado publicaciones aún.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    savedListings.forEach { listing ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            onClick = { onSelectListing(listing) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(listing.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(formatCurrency(listing.price), fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                }
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
            2 -> {
                Text("Bandeja de Entrada Directa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (messages.isEmpty()) {
                    Text("Sin mensajes recientes.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    messages.forEach { msg ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(msg.senderName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(msg.dateSent, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text("Ref: ${msg.listingTitle}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                                Spacer(Modifier.height(4.dp))
                                Text(msg.messageText, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
            3 -> {
                Text("Histórico de Facturación & PDF", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                invoices.forEach { inv ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        onClick = { onSelectInvoice(inv) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(inv.number, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(inv.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatCurrency(inv.amount), fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                            }
                            Button(onClick = { onSelectInvoice(inv) }) {
                                Text("PDF", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }
    }
}
