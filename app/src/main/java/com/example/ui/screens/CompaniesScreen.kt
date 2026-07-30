package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Company
import com.example.ui.components.CompanyCard

@Composable
fun CompaniesScreen(
    companies: List<Company>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSelectCompany: (Company) -> Unit
) {
    val filteredCompanies = remember(companies, searchQuery) {
        if (searchQuery.isBlank()) companies
        else companies.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.category.contains(searchQuery, ignoreCase = true) ||
                    it.city.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.Business, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(text = "Directorio de Empresas", fontWeight = FontWeight.Black, fontSize = 22.sp)
            }
            Text(
                text = "Empresas verificadas con catálogo de productos, servicios y atención directa",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Buscar empresa por nombre, sector o ciudad...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
        }

        if (filteredCompanies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontraron empresas con este criterio.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCompanies) { company ->
                    CompanyCard(
                        company = company,
                        onClick = { onSelectCompany(company) }
                    )
                }
            }
        }
    }
}
