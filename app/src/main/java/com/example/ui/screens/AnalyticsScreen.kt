package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PlatformStats

@Composable
fun AnalyticsScreen(
    stats: PlatformStats
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.Analytics, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text("Estadísticas Generales del Ecosistema", fontWeight = FontWeight.Black, fontSize = 20.sp)
        }

        Text("Métricas de rendimiento de publicaciones, ciudades y conversión en tiempo real.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        // Ciudades con más publicaciones (Bar Chart Representation)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ciudades con Mayor Número de Publicaciones", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(14.dp))

                stats.topCities.forEach { (city, count) ->
                    val max = 500f
                    val percentage = count / max

                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(city, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text("$count publicaciones", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { percentage },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        // Servicios más vistos
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sectores & Servicios Más Consultados", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(12.dp))

                SectorProgressRow(name = "Desarrollo Web & Software", count = "38%", value = 0.38f)
                SectorProgressRow(name = "Apartamentos & Casas", count = "29%", value = 0.29f)
                SectorProgressRow(name = "Computadores & Celulares", count = "21%", value = 0.21f)
                SectorProgressRow(name = "Vehículos & Movilidad", count = "12%", value = 0.12f)
            }
        }
    }
}

@Composable
fun SectorProgressRow(name: String, count: String, value: Float) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, fontSize = 12.sp)
            Text(count, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { value },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
