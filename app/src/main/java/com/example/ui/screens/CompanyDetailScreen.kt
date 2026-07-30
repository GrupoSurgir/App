package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Company

@Composable
fun CompanyDetailScreen(
    company: Company,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Cover Photo & Header Back
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_surgir_hero),
                contentDescription = company.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                }
            }
        }

        // Profile Logo & Core Info
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-30).dp)
                    .size(70.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon),
                    contentDescription = company.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.offset(y = (-20).dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(company.name, fontWeight = FontWeight.Black, fontSize = 22.sp)
                    Icon(Icons.Default.Verified, contentDescription = "Verificada", tint = MaterialTheme.colorScheme.primary)
                }

                Text(company.category, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(company.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Info Rows
                InfoDetailRow(icon = Icons.Default.LocationOn, label = "Dirección", value = company.address)
                InfoDetailRow(icon = Icons.Default.LocationCity, label = "Ciudad", value = company.city)
                InfoDetailRow(icon = Icons.Default.Language, label = "Sitio Web", value = company.website)
                InfoDetailRow(icon = Icons.Default.Email, label = "Correo Oficial", value = company.email)
                InfoDetailRow(icon = Icons.Default.Phone, label = "Teléfono", value = company.phone)
                InfoDetailRow(icon = Icons.Default.Schedule, label = "Horario de Atención", value = company.schedule)

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Calificación", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${company.rating} ★", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFFF59E0B))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Productos Activos", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${company.productsCount}", fontWeight = FontWeight.Black, fontSize = 18.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Servicios Ofrecidos", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${company.servicesCount}", fontWeight = FontWeight.Black, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
        Column {
            Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}
