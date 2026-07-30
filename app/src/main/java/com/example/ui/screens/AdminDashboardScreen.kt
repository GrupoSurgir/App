package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PlatformStats

@Composable
fun AdminDashboardScreen(
    stats: PlatformStats,
    onNavigateAnalytics: () -> Unit
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
            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Text("Panel de Administración SURGIR", fontWeight = FontWeight.Black, fontSize = 20.sp)
        }

        Text("Supervisión global de usuarios, empresas, facturas y registros de auditoría.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        // Metrics Grid
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(label = "Usuarios Registrados", value = "${stats.totalUsers}", icon = Icons.Default.Group, modifier = Modifier.weight(1f))
            MetricCard(label = "Empresas Activas", value = "${stats.totalCompanies}", icon = Icons.Default.Business, modifier = Modifier.weight(1f))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(label = "Publicaciones Totales", value = "${stats.totalListings}", icon = Icons.Default.ListAlt, modifier = Modifier.weight(1f))
            MetricCard(label = "Correos Enviados", value = "12,480", icon = Icons.Default.Email, modifier = Modifier.weight(1f))
        }

        Button(
            onClick = onNavigateAnalytics,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Ver Gráficas y Reportes Avanzados")
        }

        HorizontalDivider()

        // System Logs
        Text("Registros del Sistema (Logs)", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LogEntry("[2026-07-30 15:58] [AUTH] Usuario 'empresa_tech' inició sesión.")
                LogEntry("[2026-07-30 15:45] [IA_ENGINE] Análisis de imagen completado con éxito (Gemini REST API).")
                LogEntry("[2026-07-30 15:30] [INVOICE] Generada factura #FAC-2026-00105.")
                LogEntry("[2026-07-30 14:12] [MAIL_GATEWAY] Notificación de contacto entregada a 'vendedor@surgir.co'.")
            }
        }
    }
}

@Composable
fun MetricCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Black, fontSize = 20.sp)
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun LogEntry(logText: String) {
    Text(
        text = logText,
        fontSize = 10.sp,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
