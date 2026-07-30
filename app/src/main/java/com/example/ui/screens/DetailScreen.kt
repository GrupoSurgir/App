package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
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
import com.example.data.model.*
import com.example.ui.components.StatusChip
import com.example.ui.components.formatCurrency

@Composable
fun ListingDetailScreen(
    listing: Listing,
    onBack: () -> Unit,
    onToggleSave: () -> Unit,
    onContact: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Top Bar & Hero Gallery Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_surgir_hero),
                contentDescription = listing.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Top overlay bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f))
                ) {
                    Icon(
                        imageVector = if (listing.isSaved) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Guardar",
                        tint = if (listing.isSaved) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
            }
        }

        // Listing Content Body
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status = listing.status)

                Text(
                    text = "Publicado: ${listing.dateAdded}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = listing.title,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = formatCurrency(listing.price),
                fontWeight = FontWeight.Black,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(listing.location, fontWeight = FontWeight.Medium, fontSize = 13.sp)
            }

            if (listing.type == ListingType.INMOBILIARIA) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Área Total", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${listing.areaSqM.toInt()} m²", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Habitaciones", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${listing.bedrooms}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Baños", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${listing.bathrooms}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Garaje", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${listing.garage}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }

            HorizontalDivider()

            Text("Descripción Completa", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                text = listing.description,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            // Map Preview Placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Map, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                        Text("Mapa e Ubicación en Vivo", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(listing.location, fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }

            HorizontalDivider()

            // Seller Contact Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
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
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = listing.sellerName.take(1).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(listing.sellerName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("Protección de correo activa • SURGIR", fontSize = 10.sp, color = Color(0xFF10B981))
                    }

                    Button(onClick = onContact) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Enviar Mensaje")
                    }
                }
            }
        }
    }
}
