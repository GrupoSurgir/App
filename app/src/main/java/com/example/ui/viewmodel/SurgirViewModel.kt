package com.example.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AiGeneratedListingInfo
import com.example.data.ai.GeminiService
import com.example.data.local.AppDatabase
import com.example.data.model.*
import com.example.data.repository.SurgirRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

sealed interface AiAnalysisState {
    object Idle : AiAnalysisState
    object Loading : AiAnalysisState
    data class Success(val info: AiGeneratedListingInfo) : AiAnalysisState
    data class Error(val message: String) : AiAnalysisState
}

class SurgirViewModel(
    private val repository: SurgirRepository,
    private val geminiService: GeminiService = GeminiService()
) : ViewModel() {

    // Global App Preferences & State
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _userRole = MutableStateFlow(UserRole.USUARIO)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryFilter = MutableStateFlow("Todas")
    val selectedCategoryFilter: StateFlow<String> = _selectedCategoryFilter.asStateFlow()

    private val _selectedTypeFilter = MutableStateFlow<ListingType?>(null)
    val selectedTypeFilter: StateFlow<ListingType?> = _selectedTypeFilter.asStateFlow()

    // Selected Details
    private val _selectedListing = MutableStateFlow<Listing?>(null)
    val selectedListing: StateFlow<Listing?> = _selectedListing.asStateFlow()

    private val _selectedCompany = MutableStateFlow<Company?>(null)
    val selectedCompany: StateFlow<Company?> = _selectedCompany.asStateFlow()

    private val _selectedInvoiceForPdf = MutableStateFlow<Invoice?>(null)
    val selectedInvoiceForPdf: StateFlow<Invoice?> = _selectedInvoiceForPdf.asStateFlow()

    // AI Analysis State
    private val _aiAnalysisState = MutableStateFlow<AiAnalysisState>(AiAnalysisState.Idle)
    val aiAnalysisState: StateFlow<AiAnalysisState> = _aiAnalysisState.asStateFlow()

    // AI Chat History: Pair(isUser, messageText)
    private val _aiChatMessages = MutableStateFlow<List<Pair<Boolean, String>>>(
        listOf(
            false to "¡Hola! Soy el Asistente Inteligente de SURGIR WEB. Te puedo ayudar a crear publicaciones profesionales, corregir descripciones, sugerir la mejor categoría o cotizar precios estimados. ¿En qué te puedo asesorar hoy?"
        )
    )
    val aiChatMessages: StateFlow<List<Pair<Boolean, String>>> = _aiChatMessages.asStateFlow()

    // Toast / Message Notice
    private val _userNotification = MutableStateFlow<String?>(null)
    val userNotification: StateFlow<String?> = _userNotification.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    // Reactive Data Flows
    val allListings: StateFlow<List<Listing>> = repository.allListings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredListings: StateFlow<List<Listing>> = repository.featuredListings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedListings: StateFlow<List<Listing>> = repository.savedListings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val companies: StateFlow<List<Company>> = repository.allCompanies
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val invoices: StateFlow<List<Invoice>> = repository.allInvoices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val messages: StateFlow<List<Message>> = repository.allMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Listings Calculation
    val filteredListings: StateFlow<List<Listing>> = combine(
        allListings,
        searchQuery,
        selectedCategoryFilter,
        selectedTypeFilter
    ) { list, query, category, type ->
        list.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true) ||
                    item.location.contains(query, ignoreCase = true)

            val matchesCategory = category == "Todas" || item.category.equals(category, ignoreCase = true)
            val matchesType = type == null || item.type == type

            matchesQuery && matchesCategory && matchesType
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // User Actions
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setUserRole(role: UserRole) {
        _userRole.value = role
        showNotification("Modo cambiado a: ${role.name}")
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String) {
        _selectedCategoryFilter.value = category
    }

    fun setTypeFilter(type: ListingType?) {
        _selectedTypeFilter.value = type
    }

    fun selectListing(listing: Listing?) {
        _selectedListing.value = listing
        listing?.let {
            viewModelScope.launch {
                repository.incrementViews(it.id)
            }
        }
    }

    fun selectCompany(company: Company?) {
        _selectedCompany.value = company
    }

    fun selectInvoiceForPdf(invoice: Invoice?) {
        _selectedInvoiceForPdf.value = invoice
    }

    fun toggleSaveListing(listing: Listing) {
        viewModelScope.launch {
            repository.toggleSavedListing(listing.id, listing.isSaved)
            showNotification(if (!listing.isSaved) "Publicación guardada en favoritos" else "Removido de favoritos")
        }
    }

    fun clearNotification() {
        _userNotification.value = null
    }

    private fun showNotification(msg: String) {
        _userNotification.value = msg
    }

    // Submit buyer message to seller
    fun sendMessageToSeller(
        listing: Listing,
        buyerName: String,
        buyerEmail: String,
        buyerPhone: String,
        messageText: String,
        onSuccess: () -> Unit
    ) {
        if (buyerName.isBlank() || buyerEmail.isBlank() || messageText.isBlank()) {
            showNotification("Por favor completa nombre, correo y mensaje")
            return
        }

        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val message = Message(
                id = UUID.randomUUID().toString(),
                listingId = listing.id,
                listingTitle = listing.title,
                senderName = buyerName,
                senderEmail = buyerEmail,
                senderPhone = buyerPhone,
                messageText = messageText,
                dateSent = dateFormat.format(Date())
            )
            repository.insertMessage(message)
            showNotification("¡Mensaje enviado exitosamente a ${listing.sellerName}!")
            onSuccess()
        }
    }

    // AI Analysis call
    fun analyzeListingWithAi(promptText: String, imageBitmap: Bitmap? = null) {
        viewModelScope.launch {
            _aiAnalysisState.value = AiAnalysisState.Loading
            try {
                val result = geminiService.analyzeAndGenerateListing(promptText, imageBitmap)
                _aiAnalysisState.value = AiAnalysisState.Success(result)
            } catch (e: Exception) {
                _aiAnalysisState.value = AiAnalysisState.Error("Ocurrió un error analizando los datos. Inténtalo de nuevo.")
            }
        }
    }

    fun resetAiAnalysisState() {
        _aiAnalysisState.value = AiAnalysisState.Idle
    }

    // Send chat message in AI Assistant
    fun sendAiChatMessage(userMessage: String) {
        if (userMessage.isBlank()) return
        val currentList = _aiChatMessages.value.toMutableList()
        currentList.add(true to userMessage)
        _aiChatMessages.value = currentList

        viewModelScope.launch {
            val aiResponse = geminiService.chatWithAiAssistant(userMessage)
            val updatedList = _aiChatMessages.value.toMutableList()
            updatedList.add(false to aiResponse)
            _aiChatMessages.value = updatedList
        }
    }

    // Create New Listing
    fun createListing(
        title: String,
        description: String,
        price: Double,
        location: String,
        type: ListingType,
        category: String,
        sellerName: String,
        sellerEmail: String,
        sellerPhone: String,
        bedrooms: Int = 0,
        bathrooms: Int = 0,
        areaSqM: Double = 0.0,
        onSuccess: () -> Unit
    ) {
        if (title.isBlank() || price <= 0 || location.isBlank()) {
            showNotification("Ingresa un título, precio válido y ubicación")
            return
        }

        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val newListing = Listing(
                id = "usr_${System.currentTimeMillis()}",
                title = title,
                description = description,
                price = price,
                location = location,
                type = type,
                category = category,
                imageResName = "img_surgir_hero",
                status = ListingStatus.DISPONIBLE,
                viewsCount = 1,
                dateAdded = dateFormat.format(Date()),
                sellerName = sellerName.ifBlank { "Usuario SURGIR" },
                sellerEmail = sellerEmail.ifBlank { "usuario@surgir.co" },
                sellerPhone = sellerPhone.ifBlank { "+57 300 0000000" },
                isFeatured = true,
                bedrooms = bedrooms,
                bathrooms = bathrooms,
                areaSqM = areaSqM
            )
            repository.insertListing(newListing)
            showNotification("¡Publicación creada exitosamente!")
            onSuccess()
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val db = AppDatabase.getDatabase(context)
            val repo = SurgirRepository(
                db.listingDao(),
                db.companyDao(),
                db.invoiceDao(),
                db.messageDao()
            )
            return SurgirViewModel(repo) as T
        }
    }
}
