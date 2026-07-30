package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ListingType {
    MARKETPLACE,
    INMOBILIARIA,
    SERVICIOS
}

enum class ListingStatus {
    DISPONIBLE,
    EN_RENTA,
    VENDIDO;

    fun toSpanishLabel(): String = when(this) {
        DISPONIBLE -> "Disponible"
        EN_RENTA -> "En Renta"
        VENDIDO -> "Vendido"
    }
}

enum class UserRole {
    USUARIO,
    EMPRESA,
    ADMINISTRADOR
}

@Entity(tableName = "listings")
data class Listing(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val location: String,
    val type: ListingType,
    val category: String, // e.g., Computadores, Casas, Desarrollo Web
    val imageResName: String = "",
    val imageUrl: String = "",
    val status: ListingStatus = ListingStatus.DISPONIBLE,
    val viewsCount: Int = 0,
    val dateAdded: String,
    val sellerName: String,
    val sellerEmail: String,
    val sellerPhone: String,
    val isSaved: Boolean = false,
    val isFeatured: Boolean = false,
    // Real estate specific optional fields
    val areaSqM: Double = 0.0,
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val garage: Int = 0,
    // Services specific optional rating
    val rating: Float = 5.0f
)

@Entity(tableName = "companies")
data class Company(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val description: String,
    val address: String,
    val city: String,
    val website: String,
    val email: String,
    val phone: String,
    val schedule: String,
    val rating: Float = 4.8f,
    val productsCount: Int = 12,
    val servicesCount: Int = 5,
    val isFeatured: Boolean = true,
    val logoResName: String = "",
    val coverResName: String = ""
)

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey val id: String,
    val number: String,
    val clientName: String,
    val clientEmail: String,
    val date: String,
    val status: String, // "Pagada", "Pendiente", "Vencida"
    val amount: Double,
    val description: String
)

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val listingId: String,
    val listingTitle: String,
    val senderName: String,
    val senderEmail: String,
    val senderPhone: String,
    val messageText: String,
    val dateSent: String,
    val isRead: Boolean = false
)

data class PlatformStats(
    val totalVisits: Int = 148500,
    val totalListings: Int = 1240,
    val totalUsers: Int = 8920,
    val totalCompanies: Int = 340,
    val totalSalesAmount: Double = 485000.0,
    val topCities: List<Pair<String, Int>> = listOf(
        "Bogotá" to 420,
        "Medellín" to 310,
        "Cali" to 190,
        "Barranquilla" to 140,
        "Bucaramanga" to 90
    )
)
