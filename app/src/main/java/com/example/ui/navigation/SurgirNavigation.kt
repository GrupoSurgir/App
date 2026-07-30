package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.SurgirTheme
import com.example.ui.viewmodel.SurgirViewModel
import kotlinx.coroutines.launch

sealed class NavRoute(val route: String, val label: String, val icon: ImageVector) {
    object Home : NavRoute("home", "Inicio", Icons.Default.Home)
    object Marketplace : NavRoute("marketplace", "Marketplace", Icons.Default.ShoppingBag)
    object RealEstate : NavRoute("inmobiliaria", "Inmobiliaria", Icons.Default.Apartment)
    object Services : NavRoute("servicios", "Servicios", Icons.Default.Build)
    object Companies : NavRoute("empresas", "Empresas", Icons.Default.Business)
    object Analytics : NavRoute("estadisticas", "Estadísticas", Icons.Default.Analytics)
    object Invoices : NavRoute("facturas", "Facturas", Icons.Default.Description)
    object UserDashboard : NavRoute("user_dashboard", "Mi Panel", Icons.Default.Dashboard)
    object AdminDashboard : NavRoute("admin_dashboard", "Admin Panel", Icons.Default.AdminPanelSettings)
    object CreateListing : NavRoute("create_listing", "Publicar", Icons.Default.AddCircle)
    object ListingDetail : NavRoute("listing_detail", "Detalle", Icons.Default.Info)
    object CompanyDetail : NavRoute("company_detail", "Empresa", Icons.Default.Business)
    object Auth : NavRoute("auth", "Ingresar", Icons.Default.Person)
    object Settings : NavRoute("settings", "Ajustes", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurgirAppContent(viewModel: SurgirViewModel) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()
    val selectedListing by viewModel.selectedListing.collectAsStateWithLifecycle()
    val selectedCompany by viewModel.selectedCompany.collectAsStateWithLifecycle()
    val selectedInvoiceForPdf by viewModel.selectedInvoiceForPdf.collectAsStateWithLifecycle()

    val filteredListings by viewModel.filteredListings.collectAsStateWithLifecycle()
    val featuredListings by viewModel.featuredListings.collectAsStateWithLifecycle()
    val savedListings by viewModel.savedListings.collectAsStateWithLifecycle()
    val allListings by viewModel.allListings.collectAsStateWithLifecycle()
    val companies by viewModel.companies.collectAsStateWithLifecycle()
    val invoices by viewModel.invoices.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()

    val aiAnalysisState by viewModel.aiAnalysisState.collectAsStateWithLifecycle()
    val aiChatMessages by viewModel.aiChatMessages.collectAsStateWithLifecycle()
    val notificationMsg by viewModel.userNotification.collectAsStateWithLifecycle()

    var currentRoute by remember { mutableStateOf<NavRoute>(NavRoute.Home) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var showContactDialogForListing by remember { mutableStateOf<Listing?>(null) }
    var showAiChatSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(notificationMsg) {
        notificationMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearNotification()
        }
    }

    SurgirTheme(darkTheme = isDarkTheme) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(300.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("S", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                            Column {
                                Text("SURGIR WEB", fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Text("Ecosistema Empresarial", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp))

                        val drawerItems = listOf(
                            NavRoute.Home,
                            NavRoute.Marketplace,
                            NavRoute.RealEstate,
                            NavRoute.Services,
                            NavRoute.Companies,
                            NavRoute.Analytics,
                            NavRoute.Invoices,
                            if (userRole == UserRole.ADMINISTRADOR) NavRoute.AdminDashboard else NavRoute.UserDashboard,
                            NavRoute.Auth,
                            NavRoute.Settings
                        )

                        drawerItems.forEach { nav ->
                            NavigationDrawerItem(
                                label = { Text(nav.label, fontWeight = FontWeight.Medium) },
                                selected = currentRoute.route == nav.route,
                                onClick = {
                                    currentRoute = nav
                                    coroutineScope.launch { drawerState.close() }
                                },
                                icon = { Icon(nav.icon, contentDescription = null) },
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Theme Toggle inside drawer
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleTheme() }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode, contentDescription = null)
                                Text("Cambiar Tema", fontSize = 13.sp)
                            }
                            Switch(checked = isDarkTheme, onCheckedChange = { viewModel.toggleTheme() })
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    SurgirHeader(
                        userRole = userRole,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { viewModel.toggleTheme() },
                        onRoleSelect = { viewModel.setUserRole(it) },
                        onOpenSearch = {
                            currentRoute = NavRoute.Marketplace
                        },
                        onOpenMenu = {
                            coroutineScope.launch { drawerState.open() }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar(
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        val bottomNavItems = listOf(
                            NavRoute.Home,
                            NavRoute.Marketplace,
                            NavRoute.RealEstate,
                            NavRoute.Services,
                            if (userRole == UserRole.ADMINISTRADOR) NavRoute.AdminDashboard else NavRoute.UserDashboard
                        )

                        bottomNavItems.forEach { nav ->
                            val isSelected = currentRoute.route == nav.route
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentRoute = nav },
                                icon = { Icon(nav.icon, contentDescription = nav.label) },
                                label = { Text(nav.label, fontSize = 10.sp) }
                            )
                        }
                    }
                },
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentRoute) {
                        NavRoute.Home -> HomeScreen(
                            featuredListings = featuredListings,
                            recentListings = allListings.take(4),
                            featuredCompanies = companies,
                            searchQuery = searchQuery,
                            onSearchChange = {
                                viewModel.setSearchQuery(it)
                                currentRoute = NavRoute.Marketplace
                            },
                            onSelectListing = {
                                viewModel.selectListing(it)
                                currentRoute = NavRoute.ListingDetail
                            },
                            onSelectCompany = {
                                viewModel.selectCompany(it)
                                currentRoute = NavRoute.CompanyDetail
                            },
                            onToggleSaveListing = { viewModel.toggleSaveListing(it) },
                            onContactListing = { showContactDialogForListing = it },
                            onNavigateSection = { section ->
                                currentRoute = when (section) {
                                    "marketplace" -> NavRoute.Marketplace
                                    "inmobiliaria" -> NavRoute.RealEstate
                                    "servicios" -> NavRoute.Services
                                    "empresas" -> NavRoute.Companies
                                    else -> NavRoute.Home
                                }
                            },
                            onOpenAiAssistant = { showAiChatSheet = true },
                            onCreateListingClick = { currentRoute = NavRoute.CreateListing }
                        )

                        NavRoute.Marketplace -> MarketplaceScreen(
                            listings = filteredListings.filter { it.type == ListingType.MARKETPLACE },
                            selectedCategory = selectedCategory,
                            onCategorySelect = { viewModel.setCategoryFilter(it) },
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onSelectListing = {
                                viewModel.selectListing(it)
                                currentRoute = NavRoute.ListingDetail
                            },
                            onToggleSave = { viewModel.toggleSaveListing(it) },
                            onContact = { showContactDialogForListing = it },
                            onCreateClick = { currentRoute = NavRoute.CreateListing }
                        )

                        NavRoute.RealEstate -> RealEstateScreen(
                            listings = filteredListings.filter { it.type == ListingType.INMOBILIARIA },
                            selectedCategory = selectedCategory,
                            onCategorySelect = { viewModel.setCategoryFilter(it) },
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onSelectListing = {
                                viewModel.selectListing(it)
                                currentRoute = NavRoute.ListingDetail
                            },
                            onToggleSave = { viewModel.toggleSaveListing(it) },
                            onContact = { showContactDialogForListing = it },
                            onCreateClick = { currentRoute = NavRoute.CreateListing }
                        )

                        NavRoute.Services -> ServicesScreen(
                            listings = filteredListings.filter { it.type == ListingType.SERVICIOS },
                            selectedCategory = selectedCategory,
                            onCategorySelect = { viewModel.setCategoryFilter(it) },
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onSelectListing = {
                                viewModel.selectListing(it)
                                currentRoute = NavRoute.ListingDetail
                            },
                            onToggleSave = { viewModel.toggleSaveListing(it) },
                            onContact = { showContactDialogForListing = it },
                            onCreateClick = { currentRoute = NavRoute.CreateListing }
                        )

                        NavRoute.Companies -> CompaniesScreen(
                            companies = companies,
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.setSearchQuery(it) },
                            onSelectCompany = {
                                viewModel.selectCompany(it)
                                currentRoute = NavRoute.CompanyDetail
                            }
                        )

                        NavRoute.CreateListing -> CreateListingScreen(
                            aiAnalysisState = aiAnalysisState,
                            onAnalyzeAi = { prompt -> viewModel.analyzeListingWithAi(prompt) },
                            onResetAiState = { viewModel.resetAiAnalysisState() },
                            onSubmitListing = { title, desc, price, loc, type, cat, sName, sEmail, sPhone, beds, baths, area ->
                                viewModel.createListing(title, desc, price, loc, type, cat, sName, sEmail, sPhone, beds, baths, area) {
                                    currentRoute = NavRoute.Home
                                }
                            }
                        )

                        NavRoute.ListingDetail -> {
                            selectedListing?.let { listing ->
                                ListingDetailScreen(
                                    listing = listing,
                                    onBack = { currentRoute = NavRoute.Home },
                                    onToggleSave = { viewModel.toggleSaveListing(listing) },
                                    onContact = { showContactDialogForListing = listing }
                                )
                            } ?: run {
                                currentRoute = NavRoute.Home
                            }
                        }

                        NavRoute.CompanyDetail -> {
                            selectedCompany?.let { comp ->
                                CompanyDetailScreen(
                                    company = comp,
                                    onBack = { currentRoute = NavRoute.Companies }
                                )
                            } ?: run {
                                currentRoute = NavRoute.Companies
                            }
                        }

                        NavRoute.UserDashboard -> UserDashboardScreen(
                            userListings = allListings.take(3),
                            savedListings = savedListings,
                            invoices = invoices,
                            messages = messages,
                            onSelectListing = {
                                viewModel.selectListing(it)
                                currentRoute = NavRoute.ListingDetail
                            },
                            onSelectInvoice = { viewModel.selectInvoiceForPdf(it) },
                            onNavigateAnalytics = { currentRoute = NavRoute.Analytics },
                            onNavigateSettings = { currentRoute = NavRoute.Settings },
                            onLogout = { currentRoute = NavRoute.Auth }
                        )

                        NavRoute.AdminDashboard -> AdminDashboardScreen(
                            stats = PlatformStats(),
                            onNavigateAnalytics = { currentRoute = NavRoute.Analytics }
                        )

                        NavRoute.Analytics -> AnalyticsScreen(stats = PlatformStats())

                        NavRoute.Invoices -> InvoicesScreen(
                            invoices = invoices,
                            onSelectInvoiceForPdf = { viewModel.selectInvoiceForPdf(it) }
                        )

                        NavRoute.Auth -> AuthScreen(
                            onLoginSuccess = { role ->
                                viewModel.setUserRole(role)
                                currentRoute = if (role == UserRole.ADMINISTRADOR) NavRoute.AdminDashboard else NavRoute.UserDashboard
                            }
                        )

                        NavRoute.Settings -> SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { viewModel.toggleTheme() }
                        )
                    }

                    // Floating AI Assistant Trigger Button
                    FloatingActionButton(
                        onClick = { showAiChatSheet = true },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Asistente IA", tint = Color.White)
                    }
                }
            }
        }

        // Contact Modal Dialog
        showContactDialogForListing?.let { listing ->
            ContactModalDialog(
                listing = listing,
                onDismiss = { showContactDialogForListing = null },
                onSubmit = { name, email, phone, msg ->
                    viewModel.sendMessageToSeller(listing, name, email, phone, msg) {
                        showContactDialogForListing = null
                    }
                }
            )
        }

        // Invoice PDF Dialog
        selectedInvoiceForPdf?.let { invoice ->
            InvoicePdfDialog(
                invoice = invoice,
                onDismiss = { viewModel.selectInvoiceForPdf(null) },
                onDownloadPdf = {
                    viewModel.selectInvoiceForPdf(null)
                }
            )
        }

        // AI Assistant Sheet
        if (showAiChatSheet) {
            AiAssistantChatSheet(
                messages = aiChatMessages,
                onSendMessage = { viewModel.sendAiChatMessage(it) },
                onDismiss = { showAiChatSheet = false }
            )
        }
    }
}

