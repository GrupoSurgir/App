package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Invoice

@Composable
fun InvoicePdfDialog(
    invoice: Invoice,
    onDismiss: () -> Unit,
    onDownloadPdf: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Factura Electrónica", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = invoice.number, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (invoice.status == "Pagada") Color(0xFF10B981).copy(alpha = 0.15f)
                            else Color(0xFFF59E0B).copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = invoice.status.uppercase(),
                        color = if (invoice.status == "Pagada") Color(0xFF10B981) else Color(0xFFF59E0B),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Enterprise Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("SURGIR WEB PLATFORM SAS", fontWeight = FontWeight.Black, fontSize = 13.sp)
                        Text("NIT: 901.845.210-4", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Bogotá D.C., Colombia", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Fecha: ${invoice.date}", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        Text("CUFE: 8b4f...9912a", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Client Details
                Text("DATOS DEL CLIENTE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Nombre / Razón Social: ${invoice.clientName}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Text("Correo Electrónico: ${invoice.clientEmail}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Item breakdown
                Text("DETALLE DE CONCEPTOS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(invoice.description, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(formatCurrency(invoice.amount), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("TOTAL A PAGAR:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(formatCurrency(invoice.amount), fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                }

                // Architecture notice
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "Arquitectura lista para integración con proveedores de Facturación Electrónica DIAN / API REST.",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDownloadPdf) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(6.dp))
                Text("Descargar PDF")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantChatSheet(
    messages: List<Pair<Boolean, String>>,
    onSendMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var chatInputText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(16.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Asistente Inteligente SURGIR IA", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Generación de contenido, análisis y optimización", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { (isUser, text) ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (isUser) 16.dp else 4.dp,
                                bottomEnd = if (isUser) 4.dp else 16.dp
                            ),
                            color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = text,
                                fontSize = 13.sp,
                                color = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Input Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = chatInputText,
                    onValueChange = { chatInputText = it },
                    placeholder = { Text("Escribe una pregunta o solicita ayuda con tu publicación...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true
                )

                IconButton(
                    onClick = {
                        if (chatInputText.isNotBlank()) {
                            onSendMessage(chatInputText)
                            chatInputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar", tint = Color.White)
                }
            }
        }
    }
}
