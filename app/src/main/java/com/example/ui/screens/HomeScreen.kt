package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.*
import com.example.ui.components.*

@Composable
fun HomeScreen(
    featuredListings: List<Listing>,
    recentListings: List<Listing>,
    featuredCompanies: List<Company>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSelectListing: (Listing) -> Unit,
    onSelectCompany: (Company) -> Unit,
    onToggleSaveListing: (Listing) -> Unit,
    onContactListing: (Listing) -> Unit,
    onNavigateSection: (String) -> Unit,
    onOpenAiAssistant: () -> Unit,
    onCreateListingClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp)
    ) {
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_surgir_hero),
                contentDescription = "SURGIR Hero",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, Color.Cyan)))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Cyan, modifier = Modifier.size(14.dp))
                        Text("Ecosistema Digital de Próxima Generación", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Conecta Personas, Empresas y Clientes en Colombia",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    lineHeight = 28.sp
                )

                Text(
                    text = "Marketplace • Inmobiliaria • Servicios Profesionales • Módulo de IA",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(Modifier.height(14.dp))

                // Smart Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Buscar productos, inmuebles, servicios o empresas...", fontSize = 12.sp, color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                    trailingIcon = {
                        IconButton(onClick = onOpenAiAssistant) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = "Asistente IA", tint = Color.Cyan)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Categories Grid Quick Nav
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Ecosistemas Principales", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickCategoryCard(
                    title = "Marketplace",
                    subtitle = "Productos & Equipos",
                    icon = Icons.Default.ShoppingBag,
                    color = Color(0xFF0284C7),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSection("marketplace") }
                )
                QuickCategoryCard(
                    title = "Inmobiliaria",
                    subtitle = "Casas, Aptos & Lotes",
                    icon = Icons.Default.Apartment,
                    color = Color(0xFF10B981),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSection("inmobiliaria") }
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickCategoryCard(
                    title = "Servicios",
                    subtitle = "Profesionales & Dev",
                    icon = Icons.Default.Build,
                    color = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSection("servicios") }
                )
                QuickCategoryCard(
                    title = "Empresas",
                    subtitle = "Directorio Oficial",
                    icon = Icons.Default.Business,
                    color = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateSection("empresas") }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // AI Banner Prompt Callout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onCreateListingClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Crear Publicación con IA", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text("Sube una foto y deja que la IA genere el título, descripción y precio sugerido.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                }

                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Publicaciones Destacadas
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Publicaciones Destacadas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { onNavigateSection("marketplace") }) {
                    Text("Ver todas", fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(8.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(featuredListings) { listing ->
                    Box(modifier = Modifier.width(280.dp)) {
                        ListingCard(
                            listing = listing,
                            onClick = { onSelectListing(listing) },
                            onToggleSave = { onToggleSaveListing(listing) },
                            onContact = { onContactListing(listing) },
                            onShare = { }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Empresas Destacadas
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Empresas Destacadas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { onNavigateSection("empresas") }) {
                    Text("Directorio", fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                featuredCompanies.forEach { company ->
                    CompanyCard(
                        company = company,
                        onClick = { onSelectCompany(company) }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // General Ecosystem Statistics Summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Estadísticas Generales del Ecosistema", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatSummaryItem(label = "Visitas Totales", value = "148,500+", icon = Icons.Default.Visibility)
                    StatSummaryItem(label = "Publicaciones", value = "1,240+", icon = Icons.Default.ListAlt)
                    StatSummaryItem(label = "Empresas", value = "340+", icon = Icons.Default.Business)
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        // Professional Footer
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("SURGIR WEB PLATFORM", fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.sp)
                Text("Conectando personas, empresas y soluciones digitales.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Términos", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Privacidad", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Contacto", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Soporte", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(12.dp))
                Text("© 2026 SURGIR WEB. Todos los derechos reservados.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun QuickCategoryCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .border(width = 1.dp, color = color.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }

            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun StatSummaryItem(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
        Text(value, fontWeight = FontWeight.Black, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))
        Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
