package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var languageSelected by remember { mutableStateOf("Español") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text("Configuración de Plataforma", fontWeight = FontWeight.Black, fontSize = 20.sp)
        }

        // Appearance
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Apariencia & Tema", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode, contentDescription = null)
                        Text(if (isDarkTheme) "Modo Oscuro (Cyberpunk Space)" else "Modo Claro (Cyberpunk White)", fontSize = 13.sp)
                    }

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onToggleTheme() }
                    )
                }
            }
        }

        // Preferences
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Preferencias del Usuario", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                        Text("Notificaciones de Mensajes & Ofertas", fontSize = 13.sp)
                    }
                    Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Language, contentDescription = null)
                        Text("Idioma de la Interfaz", fontSize = 13.sp)
                    }
                    Text(languageSelected, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // Security & Privacy
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Seguridad & Privacidad", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("Protección de Correos en Contacto Directo", fontSize = 12.sp)
                }

                OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                    Text("Cambiar Contraseña")
                }
            }
        }
    }
}
