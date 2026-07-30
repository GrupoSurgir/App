package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ListingType
import com.example.ui.viewmodel.AiAnalysisState

@Composable
fun CreateListingScreen(
    aiAnalysisState: AiAnalysisState,
    onAnalyzeAi: (prompt: String) -> Unit,
    onResetAiState: () -> Unit,
    onSubmitListing: (
        title: String,
        description: String,
        price: Double,
        location: String,
        type: ListingType,
        category: String,
        sellerName: String,
        sellerEmail: String,
        sellerPhone: String,
        bedrooms: Int,
        bathrooms: Int,
        areaSqM: Double
    ) -> Unit
) {
    var userPromptText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(ListingType.MARKETPLACE) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Computadores") }
    var priceStr by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Bogotá, D.C.") }
    var sellerName by remember { mutableStateOf("Usuario SURGIR") }
    var sellerEmail by remember { mutableStateOf("usuario@surgir.co") }
    var sellerPhone by remember { mutableStateOf("+57 300 1234567") }

    // Real estate optional fields
    var bedroomsStr by remember { mutableStateOf("0") }
    var bathroomsStr by remember { mutableStateOf("0") }
    var areaStr by remember { mutableStateOf("0") }

    var qualityScore by remember { mutableIntStateOf(0) }
    var feedbackTips by remember { mutableStateOf<List<String>>(emptyList()) }

    // Update form when AI completes analysis
    LaunchedEffect(aiAnalysisState) {
        if (aiAnalysisState is AiAnalysisState.Success) {
            val info = aiAnalysisState.info
            title = info.title
            description = info.description
            category = info.category
            priceStr = info.estimatedPrice.toInt().toString()
            qualityScore = info.qualityScore
            feedbackTips = info.feedbackTips
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Crear Nueva Publicación", fontWeight = FontWeight.Black, fontSize = 22.sp)

        // Section Type Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedType == ListingType.MARKETPLACE,
                onClick = { selectedType = ListingType.MARKETPLACE },
                label = { Text("Marketplace", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedType == ListingType.INMOBILIARIA,
                onClick = { selectedType = ListingType.INMOBILIARIA },
                label = { Text("Inmobiliaria", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedType == ListingType.SERVICIOS,
                onClick = { selectedType = ListingType.SERVICIOS },
                label = { Text("Servicios", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
        }

        // AI Assistant Auto-Generator Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("Asistente de IA para Publicaciones", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Text(
                    text = "Escribe una frase corta de lo que deseas publicar o sube datos y la IA generará el título, descripción optimizada, categoría y precio estimado.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = userPromptText,
                    onValueChange = { userPromptText = it },
                    placeholder = { Text("Ejemplo: Vendo laptop Asus Rog Strix i7 32gb ram casi nueva") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = { onAnalyzeAi(userPromptText) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = aiAnalysisState !is AiAnalysisState.Loading
                ) {
                    if (aiAnalysisState is AiAnalysisState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Analizando con Gemini IA...")
                    } else {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Analizar y Generar con IA")
                    }
                }

                if (qualityScore > 0) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Calidad de la Publicación", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text("$qualityScore / 100", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                            }
                            Spacer(Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { qualityScore / 100f },
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Spacer(Modifier.height(6.dp))
                            feedbackTips.forEach { tip ->
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                                    Text(tip, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        // Manual Form / Edit Fields
        Text("Información de la Publicación", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título de la Publicación") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción Detallada") },
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = priceStr,
                onValueChange = { priceStr = it },
                label = { Text("Precio (COP)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Ubicación (Ciudad, Zona)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (selectedType == ListingType.INMOBILIARIA) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = bedroomsStr,
                    onValueChange = { bedroomsStr = it },
                    label = { Text("Habitaciones") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = bathroomsStr,
                    onValueChange = { bathroomsStr = it },
                    label = { Text("Baños") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = areaStr,
                    onValueChange = { areaStr = it },
                    label = { Text("Área (m²)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }

        Text("Datos del Anunciante / Vendedor", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        OutlinedTextField(
            value = sellerName,
            onValueChange = { sellerName = it },
            label = { Text("Nombre o Empresa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = sellerEmail,
                onValueChange = { sellerEmail = it },
                label = { Text("Correo (Protegido)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = sellerPhone,
                onValueChange = { sellerPhone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                val bedrooms = bedroomsStr.toIntOrNull() ?: 0
                val bathrooms = bathroomsStr.toIntOrNull() ?: 0
                val areaSqM = areaStr.toDoubleOrNull() ?: 0.0

                onSubmitListing(
                    title, description, price, location, selectedType,
                    category, sellerName, sellerEmail, sellerPhone,
                    bedrooms, bathrooms, areaSqM
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Publicar Ahora", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
