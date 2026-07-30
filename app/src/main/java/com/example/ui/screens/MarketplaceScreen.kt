package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Listing
import com.example.ui.components.CategoryPillsBar
import com.example.ui.components.ListingCard

@Composable
fun MarketplaceScreen(
    listings: List<Listing>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSelectListing: (Listing) -> Unit,
    onToggleSave: (Listing) -> Unit,
    onContact: (Listing) -> Unit,
    onCreateClick: () -> Unit
) {
    val categories = listOf("Todas", "Computadores", "Celulares", "Consolas", "Vehículos", "Electrodomésticos", "Herramientas")

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Publicar Producto", fontWeight = FontWeight.Bold) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Title & Search
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Marketplace SURGIR",
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp
                )
                Text(
                    text = "Encuentra y publica tecnología, vehículos, herramientas y electrodomésticos",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Buscar en Marketplace...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true
                )
            }

            // Category Filter
            CategoryPillsBar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelect
            )

            Spacer(Modifier.height(10.dp))

            // Listings List
            if (listings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No se encontraron productos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Intenta con otro término de búsqueda o categoría",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listings) { listing ->
                        ListingCard(
                            listing = listing,
                            onClick = { onSelectListing(listing) },
                            onToggleSave = { onToggleSave(listing) },
                            onContact = { onContact(listing) },
                            onShare = { }
                        )
                    }
                }
            }
        }
    }
}
