package com.example.data.repository

import com.example.data.local.CompanyDao
import com.example.data.local.InvoiceDao
import com.example.data.local.ListingDao
import com.example.data.local.MessageDao
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SurgirRepository(
    private val listingDao: ListingDao,
    private val companyDao: CompanyDao,
    private val invoiceDao: InvoiceDao,
    private val messageDao: MessageDao
) {
    val allListings: Flow<List<Listing>> = listingDao.getAllListings()
    val featuredListings: Flow<List<Listing>> = listingDao.getFeaturedListings()
    val savedListings: Flow<List<Listing>> = listingDao.getSavedListings()
    val allCompanies: Flow<List<Company>> = companyDao.getAllCompanies()
    val featuredCompanies: Flow<List<Company>> = companyDao.getFeaturedCompanies()
    val allInvoices: Flow<List<Invoice>> = invoiceDao.getAllInvoices()
    val allMessages: Flow<List<Message>> = messageDao.getAllMessages()

    suspend fun getListingsByType(type: ListingType): Flow<List<Listing>> {
        return listingDao.getListingsByType(type)
    }

    suspend fun toggleSavedListing(id: String, currentSavedState: Boolean) {
        listingDao.updateSavedStatus(id, !currentSavedState)
    }

    suspend fun incrementViews(id: String) {
        listingDao.incrementViews(id)
    }

    suspend fun insertListing(listing: Listing) {
        listingDao.insertListing(listing)
    }

    suspend fun deleteListing(listing: Listing) {
        listingDao.deleteListing(listing)
    }

    suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    suspend fun seedInitialDataIfEmpty() {
        val existing = allListings.first()
        if (existing.isNotEmpty()) return

        // Seed initial Marketplace, Inmobiliaria, and Servicios listings
        val sampleListings = listOf(
            // Marketplace
            Listing(
                id = "m1",
                title = "MacBook Pro M3 Max 16\" 36GB RAM 1TB",
                description = "Equipo empresarial en estado impecable 10/10. Incluye cargador original MagSafe y funda de cuero protectora.",
                price = 12500000.0,
                location = "Bogotá, D.C.",
                type = ListingType.MARKETPLACE,
                category = "Computadores",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 342,
                dateAdded = "2026-07-28",
                sellerName = "TechCorp Colombia",
                sellerEmail = "ventas@techcorp.co",
                sellerPhone = "+57 310 9876543",
                isFeatured = true
            ),
            Listing(
                id = "m2",
                title = "iPhone 15 Pro Max 256GB Titanium",
                description = "Batería al 98%, libre de todo registro. Factura original y garantía vigente con Apple.",
                price = 4800000.0,
                location = "Medellín, Antioquia",
                type = ListingType.MARKETPLACE,
                category = "Celulares",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 512,
                dateAdded = "2026-07-29",
                sellerName = "Carlos Mendoza",
                sellerEmail = "carlos.m@surgir.com",
                sellerPhone = "+57 300 1234567",
                isFeatured = true
            ),
            Listing(
                id = "m3",
                title = "PlayStation 5 Slim 1TB + 2 Controles DualSense",
                description = "Consola edición especial con juegos incluidos en formato digital y base de carga vertical.",
                price = 2200000.0,
                location = "Cali, Valle del Cauca",
                type = ListingType.MARKETPLACE,
                category = "Consolas",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 189,
                dateAdded = "2026-07-25",
                sellerName = "GamingZone Studio",
                sellerEmail = "contacto@gamingzone.co",
                sellerPhone = "+57 312 4567890"
            ),
            Listing(
                id = "m4",
                title = "Toyota TXL 2.8 Diesel Turbo 4x4 - Modelo 2024",
                description = "Vehículo empresarial único dueño. Cojinería en cuero, techo panorámico, mantenimiento en concesionario oficial.",
                price = 310000000.0,
                location = "Barranquilla, Atlántico",
                type = ListingType.MARKETPLACE,
                category = "Vehículos",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 890,
                dateAdded = "2026-07-20",
                sellerName = "AutoPremium SAS",
                sellerEmail = "gerencia@autopremium.co",
                sellerPhone = "+57 318 8889900",
                isFeatured = true
            ),

            // Inmobiliaria
            Listing(
                id = "i1",
                title = "Penthouse Dúplex de Lujo con Vista Panorámica",
                description = "Espectacular penthouse en Rosales con acabados de diseñador, terraza de 45m² con jacuzzi, automatización inteligente Crestron.",
                price = 1850000000.0,
                location = "Bogotá - Rosales",
                type = ListingType.INMOBILIARIA,
                category = "Apartamentos",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 1240,
                dateAdded = "2026-07-27",
                sellerName = "Luxe Real Estate Colombia",
                sellerEmail = "inmuebles@luxerealestate.co",
                sellerPhone = "+57 310 5551234",
                isFeatured = true,
                areaSqM = 280.0,
                bedrooms = 4,
                bathrooms = 5,
                garage = 3
            ),
            Listing(
                id = "i2",
                title = "Casa Campestre Moderna en Llano Grande",
                description = "Casa inteligente rodeada de naturaleza con piscina climatizada, zona BBQ independiente y parqueadero cubierto para 4 vehículos.",
                price = 2400000000.0,
                location = "Rionegro / Llano Grande",
                type = ListingType.INMOBILIARIA,
                category = "Casas",
                imageResName = "img_surgir_hero",
                status = ListingStatus.EN_RENTA,
                viewsCount = 670,
                dateAdded = "2026-07-26",
                sellerName = "Surgir Inmobiliaria Prime",
                sellerEmail = "info@surgirinmobiliaria.com",
                sellerPhone = "+57 301 7778899",
                isFeatured = true,
                areaSqM = 520.0,
                bedrooms = 5,
                bathrooms = 6,
                garage = 4
            ),
            Listing(
                id = "i3",
                title = "Oficina Corporativa AAA en Centro Empresarial",
                description = "Oficina amoblada de alto estándar con salas de juntas integradas, control de acceso biométrico y vista a los cerros.",
                price = 8500000.0,
                location = "Bogotá - Calle 100",
                type = ListingType.INMOBILIARIA,
                category = "Oficinas",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 410,
                dateAdded = "2026-07-22",
                sellerName = "Inversiones Urbanas SAS",
                sellerEmail = "comercial@inversionesurbanas.co",
                sellerPhone = "+57 315 3332211",
                areaSqM = 145.0,
                bedrooms = 0,
                bathrooms = 2,
                garage = 2
            ),

            // Servicios
            Listing(
                id = "s1",
                title = "Desarrollo Web Full Stack & E-Commerce Next.js / Mobile",
                description = "Creación de plataformas empresariales escalables, integración de pasarelas de pago, IA, optimización SEO y diseño UI/UX premium.",
                price = 3500000.0,
                location = "Remoto / Colombia",
                type = ListingType.SERVICIOS,
                category = "Desarrollo Web",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 980,
                dateAdded = "2026-07-29",
                sellerName = "Surgir Digital Lab",
                sellerEmail = "hola@surgirdigital.co",
                sellerPhone = "+57 316 0001122",
                isFeatured = true,
                rating = 4.9f
            ),
            Listing(
                id = "s2",
                title = "Estrategia de Branding & Diseños de Identidad Corporativa",
                description = "Diseño de marcas de alto impacto, manual de identidad, tipografía, paleta de colores y componentes UI adaptativos.",
                price = 1800000.0,
                location = "Medellín, Colombia",
                type = ListingType.SERVICIOS,
                category = "Diseño Gráfico",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 350,
                dateAdded = "2026-07-24",
                sellerName = "Linear Design Studio",
                sellerEmail = "contacto@lineardesign.co",
                sellerPhone = "+57 320 4445566",
                rating = 5.0f
            ),
            Listing(
                id = "s3",
                title = "Fotografía Corporativa & Producción Audiovisual con Drone 4K",
                description = "Cobertura de eventos empresariales, fotos de catálogo para productos y tomas aéreas cinemáticas en resolución 4K.",
                price = 1200000.0,
                location = "Cali, Valle del Cauca",
                type = ListingType.SERVICIOS,
                category = "Fotografía",
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 290,
                dateAdded = "2026-07-21",
                sellerName = "Apex Media Productions",
                sellerEmail = "media@apex.co",
                sellerPhone = "+57 311 6667788",
                rating = 4.8f
            )
        )

        listingDao.insertAllListings(sampleListings)

        // Seed initial Companies
        val sampleCompanies = listOf(
            Company(
                id = "c1",
                name = "Surgir Ecosistema Digital SAS",
                category = "Tecnología & Software",
                description = "Plataforma líder en innovación empresarial, desarrollo Full Stack e integración de soluciones basadas en Inteligencia Artificial.",
                address = "Av. El Dorado #68-90, Edificio Innovación P-8",
                city = "Bogotá, D.C.",
                website = "https://surgirweb.co",
                email = "contacto@surgirweb.co",
                phone = "+57 (601) 745 9000",
                schedule = "Lun - Vie: 8:00 AM - 6:00 PM",
                rating = 5.0f,
                productsCount = 18,
                servicesCount = 8,
                isFeatured = true,
                logoResName = "img_app_icon",
                coverResName = "img_surgir_hero"
            ),
            Company(
                id = "c2",
                name = "Luxe Real Estate Group",
                category = "Inmobiliaria & Desarrollo Urbano",
                description = "Firma inmobiliaria especializada en propiedades de alto formato, proyectos inteligentes y consultoría de inversión de bienes raíces.",
                address = "Calle 93B #13-45, Oficina 402",
                city = "Bogotá - Chico",
                website = "https://luxerealestate.co",
                email = "info@luxerealestate.co",
                phone = "+57 310 5551234",
                schedule = "Lun - Sáb: 9:00 AM - 7:00 PM",
                rating = 4.9f,
                productsCount = 42,
                servicesCount = 6,
                isFeatured = true,
                logoResName = "img_app_icon",
                coverResName = "img_surgir_hero"
            ),
            Company(
                id = "c3",
                name = "AutoPremium Colombia",
                category = "Automotriz & Movilidad",
                description = "Concesionario especializado en vehículos de alta gama, blindaje certificado y asesoría de flotas corporativas.",
                address = "Carrera 43A #1Sur-100",
                city = "Medellín - El Poblado",
                website = "https://autopremium.co",
                email = "ventas@autopremium.co",
                phone = "+57 318 8889900",
                schedule = "Lun - Sáb: 8:30 AM - 6:30 PM",
                rating = 4.8f,
                productsCount = 25,
                servicesCount = 4,
                isFeatured = true,
                logoResName = "img_app_icon",
                coverResName = "img_surgir_hero"
            )
        )

        companyDao.insertAllCompanies(sampleCompanies)

        // Seed initial Invoices
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDateStr = dateFormat.format(Date())

        val sampleInvoices = listOf(
            Invoice(
                id = "inv1",
                number = "FAC-2026-00104",
                clientName = "TechCorp Colombia SAS",
                clientEmail = "facturacion@techcorp.co",
                date = currentDateStr,
                status = "Pagada",
                amount = 4850000.0,
                description = "Publicación Destacada Plan Platinum + Módulo IA Surgir Web"
            ),
            Invoice(
                id = "inv2",
                number = "FAC-2026-00105",
                clientName = "Inmobiliaria Prime SAS",
                clientEmail = "administracion@prime.co",
                date = currentDateStr,
                status = "Pendiente",
                amount = 1250000.0,
                description = "Suscripción Ecosistema Inmobiliario Mensual"
            ),
            Invoice(
                id = "inv3",
                number = "FAC-2026-00098",
                clientName = "Alejandro Gómez",
                clientEmail = "alejandro.g@gmail.com",
                date = "2026-07-15",
                status = "Pagada",
                amount = 350000.0,
                description = "Verificación de Perfil de Empresa & Dominio SURGIR"
            )
        )

        invoiceDao.insertAllInvoices(sampleInvoices)

        // Seed initial Messages
        val sampleMessages = listOf(
            Message(
                id = "msg1",
                listingId = "m1",
                listingTitle = "MacBook Pro M3 Max 16\"",
                senderName = "Santiago Restrepo",
                senderEmail = "santiago.r@empresa.co",
                senderPhone = "+57 314 1112233",
                messageText = "Hola, estoy interesado en el MacBook Pro M3. ¿Hacen envíos a Medellín y entregan factura electrónica?",
                dateSent = "2026-07-30 14:20"
            ),
            Message(
                id = "msg2",
                listingId = "i1",
                listingTitle = "Penthouse Dúplex de Lujo",
                senderName = "María Fernanda Silva",
                senderEmail = "mf.silva@arquitectura.co",
                senderPhone = "+57 300 9998877",
                messageText = "Buenas tardes. Quisiera programar una visita privada para conocer el penthouse en Rosales este viernes en la mañana.",
                dateSent = "2026-07-29 09:45"
            )
        )

        sampleMessages.forEach { messageDao.insertMessage(it) }
    }
}
