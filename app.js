// State Data matching Kotlin SurgirRepository.kt
const state = {
  theme: 'dark',
  role: 'USUARIO',
  currentRoute: 'home',
  searchQuery: '',

  listings: [
    {
      id: "m1",
      title: "MacBook Pro M3 Max 16\" 36GB RAM 1TB",
      description: "Equipo empresarial en estado impecable 10/10. Incluye cargador original MagSafe y funda de cuero protectora.",
      price: 12500000.0,
      location: "Bogotá, D.C.",
      type: "MARKETPLACE",
      category: "Computadores",
      icon: "fa-laptop",
      status: "Disponible",
      viewsCount: 342,
      dateAdded: "2026-07-28",
      sellerName: "TechCorp Colombia",
      sellerEmail: "ventas@techcorp.co",
      sellerPhone: "+57 310 9876543",
      isSaved: false,
      isFeatured: true
    },
    {
      id: "m2",
      title: "iPhone 15 Pro Max 256GB Titanium",
      description: "Batería al 98%, libre de todo registro. Factura original y garantía vigente con Apple.",
      price: 4800000.0,
      location: "Medellín, Antioquia",
      type: "MARKETPLACE",
      category: "Celulares",
      icon: "fa-mobile-screen-button",
      status: "Disponible",
      viewsCount: 512,
      dateAdded: "2026-07-29",
      sellerName: "Carlos Mendoza",
      sellerEmail: "carlos.m@surgir.com",
      sellerPhone: "+57 300 1234567",
      isSaved: false,
      isFeatured: true
    },
    {
      id: "m3",
      title: "PlayStation 5 Slim 1TB + 2 Controles DualSense",
      description: "Consola edición especial con juegos incluidos en formato digital y base de carga vertical.",
      price: 2200000.0,
      location: "Cali, Valle del Cauca",
      type: "MARKETPLACE",
      category: "Consolas",
      icon: "fa-gamepad",
      status: "Disponible",
      viewsCount: 189,
      dateAdded: "2026-07-25",
      sellerName: "GamingZone Studio",
      sellerEmail: "contacto@gamingzone.co",
      sellerPhone: "+57 312 4567890",
      isSaved: false,
      isFeatured: false
    },
    {
      id: "m4",
      title: "Toyota TXL 2.8 Diesel Turbo 4x4 - Modelo 2024",
      description: "Vehículo empresarial único dueño. Cojinería en cuero, techo panorámico, mantenimiento en concesionario oficial.",
      price: 310000000.0,
      location: "Barranquilla, Atlántico",
      type: "MARKETPLACE",
      category: "Vehículos",
      icon: "fa-car",
      status: "Disponible",
      viewsCount: 890,
      dateAdded: "2026-07-20",
      sellerName: "AutoPremium SAS",
      sellerEmail: "gerencia@autopremium.co",
      sellerPhone: "+57 318 8889900",
      isSaved: true,
      isFeatured: true
    },
    {
      id: "i1",
      title: "Penthouse Dúplex de Lujo con Vista Panorámica",
      description: "Espectacular penthouse en Rosales con acabados de diseñador, terraza de 45m² con jacuzzi, automatización inteligente Crestron.",
      price: 1850000000.0,
      location: "Bogotá - Rosales",
      type: "INMOBILIARIA",
      category: "Apartamentos",
      icon: "fa-building",
      status: "Disponible",
      viewsCount: 1240,
      dateAdded: "2026-07-27",
      sellerName: "Luxe Real Estate Colombia",
      sellerEmail: "inmuebles@luxerealestate.co",
      sellerPhone: "+57 310 5551234",
      isSaved: true,
      isFeatured: true,
      areaSqM: 280.0,
      bedrooms: 4,
      bathrooms: 5,
      garage: 3
    },
    {
      id: "i2",
      title: "Casa Campestre Moderna en Llano Grande",
      description: "Casa inteligente rodeada de naturaleza con piscina climatizada, zona BBQ independiente y parqueadero cubierto para 4 vehículos.",
      price: 2400000000.0,
      location: "Rionegro / Llano Grande",
      type: "INMOBILIARIA",
      category: "Casas",
      icon: "fa-house-chimney",
      status: "En Renta",
      viewsCount: 670,
      dateAdded: "2026-07-26",
      sellerName: "Surgir Inmobiliaria Prime",
      sellerEmail: "info@surgirinmobiliaria.com",
      sellerPhone: "+57 301 7778899",
      isSaved: false,
      isFeatured: true,
      areaSqM: 520.0,
      bedrooms: 5,
      bathrooms: 6,
      garage: 4
    },
    {
      id: "i3",
      title: "Oficina Corporativa AAA en Centro Empresarial",
      description: "Oficina amoblada de alto estándar con salas de juntas integradas, control de acceso biométrico y vista a los cerros.",
      price: 8500000.0,
      location: "Bogotá - Calle 100",
      type: "INMOBILIARIA",
      category: "Oficinas",
      icon: "fa-briefcase",
      status: "Disponible",
      viewsCount: 410,
      dateAdded: "2026-07-22",
      sellerName: "Inversiones Urbanas SAS",
      sellerEmail: "comercial@inversionesurbanas.co",
      sellerPhone: "+57 315 3332211",
      isSaved: false,
      isFeatured: false,
      areaSqM: 145.0,
      bedrooms: 0,
      bathrooms: 2,
      garage: 2
    },
    {
      id: "s1",
      title: "Desarrollo Web Full Stack & E-Commerce Next.js / Mobile",
      description: "Creación de plataformas empresariales escalables, integración de pasarelas de pago, IA, optimización SEO y diseño UI/UX premium.",
      price: 3500000.0,
      location: "Remoto / Colombia",
      type: "SERVICIOS",
      category: "Desarrollo Web",
      icon: "fa-code",
      status: "Disponible",
      viewsCount: 980,
      dateAdded: "2026-07-29",
      sellerName: "Surgir Digital Lab",
      sellerEmail: "hola@surgirdigital.co",
      sellerPhone: "+57 316 0001122",
      isSaved: false,
      isFeatured: true,
      rating: 4.9
    },
    {
      id: "s2",
      title: "Estrategia de Branding & Diseños de Identidad Corporativa",
      description: "Diseño de marcas de alto impacto, manual de identidad, tipografía, paleta de colores y componentes UI adaptativos.",
      price: 1800000.0,
      location: "Medellín, Colombia",
      type: "SERVICIOS",
      category: "Diseño Gráfico",
      icon: "fa-pen-nib",
      status: "Disponible",
      viewsCount: 350,
      dateAdded: "2026-07-24",
      sellerName: "Linear Design Studio",
      sellerEmail: "contacto@lineardesign.co",
      sellerPhone: "+57 320 4445566",
      isSaved: false,
      isFeatured: false,
      rating: 5.0
    },
    {
      id: "s3",
      title: "Fotografía Corporativa & Producción Audiovisual con Drone 4K",
      description: "Cobertura de eventos empresariales, fotos de catálogo para productos y tomas aéreas cinemáticas en resolución 4K.",
      price: 1200000.0,
      location: "Cali, Valle del Cauca",
      type: "SERVICIOS",
      category: "Fotografía",
      icon: "fa-camera",
      status: "Disponible",
      viewsCount: 290,
      dateAdded: "2026-07-21",
      sellerName: "Apex Media Productions",
      sellerEmail: "media@apex.co",
      sellerPhone: "+57 311 6667788",
      isSaved: false,
      isFeatured: false,
      rating: 4.8
    }
  ],

  companies: [
    {
      id: "c1",
      name: "Surgir Ecosistema Digital SAS",
      category: "Tecnología & Software",
      description: "Plataforma líder en innovación empresarial, desarrollo Full Stack e integración de soluciones basadas en Inteligencia Artificial.",
      address: "Av. El Dorado #68-90, Edificio Innovación P-8",
      city: "Bogotá, D.C.",
      website: "https://surgirweb.co",
      email: "contacto@surgirweb.co",
      phone: "+57 (601) 745 9000",
      schedule: "Lun - Vie: 8:00 AM - 6:00 PM",
      rating: 5.0,
      productsCount: 18,
      servicesCount: 8
    },
    {
      id: "c2",
      name: "Luxe Real Estate Group",
      category: "Inmobiliaria & Desarrollo Urbano",
      description: "Firma inmobiliaria especializada en propiedades de alto formato, proyectos inteligentes y consultoría de inversión de bienes raíces.",
      address: "Calle 93B #13-45, Oficina 402",
      city: "Bogotá - Chicó",
      website: "https://luxerealestate.co",
      email: "info@luxerealestate.co",
      phone: "+57 310 5551234",
      schedule: "Lun - Sáb: 9:00 AM - 7:00 PM",
      rating: 4.9,
      productsCount: 42,
      servicesCount: 6
    },
    {
      id: "c3",
      name: "AutoPremium Colombia",
      category: "Automotriz & Movilidad",
      description: "Concesionario especializado en vehículos de alta gama, blindaje certificado y asesoría de flotas corporativas.",
      address: "Carrera 43A #1Sur-100",
      city: "Medellín - El Poblado",
      website: "https://autopremium.co",
      email: "ventas@autopremium.co",
      phone: "+57 318 8889900",
      schedule: "Lun - Sáb: 8:30 AM - 6:30 PM",
      rating: 4.8,
      productsCount: 25,
      servicesCount: 4
    }
  ],

  invoices: [
    {
      id: "inv1",
      number: "FAC-2026-00104",
      clientName: "TechCorp Colombia SAS",
      clientEmail: "facturacion@techcorp.co",
      date: "2026-07-30",
      status: "Pagada",
      amount: 4850000.0,
      description: "Publicación Destacada Plan Platinum + Módulo IA Surgir Web"
    },
    {
      id: "inv2",
      number: "FAC-2026-00105",
      clientName: "Inmobiliaria Prime SAS",
      clientEmail: "administracion@prime.co",
      date: "2026-07-30",
      status: "Pendiente",
      amount: 1250000.0,
      description: "Suscripción Ecosistema Inmobiliario Mensual"
    },
    {
      id: "inv3",
      number: "FAC-2026-00098",
      clientName: "Alejandro Gómez",
      clientEmail: "alejandro.g@gmail.com",
      date: "2026-07-15",
      status: "Pagada",
      amount: 350000.0,
      description: "Verificación de Perfil de Empresa & Dominio SURGIR"
    }
  ],

  messages: [
    {
      id: "msg1",
      listingTitle: "MacBook Pro M3 Max 16\"",
      senderName: "Santiago Restrepo",
      senderEmail: "santiago.r@empresa.co",
      messageText: "Hola, estoy interesado en el MacBook Pro M3. ¿Hacen envíos a Medellín y entregan factura electrónica?",
      dateSent: "2026-07-30 14:20"
    },
    {
      id: "msg2",
      listingTitle: "Penthouse Dúplex de Lujo",
      senderName: "María Fernanda Silva",
      senderEmail: "mf.silva@arquitectura.co",
      messageText: "Buenas tardes. Quisiera programar una visita privada para conocer el penthouse en Rosales este viernes en la mañana.",
      dateSent: "2026-07-29 09:45"
    }
  ],

  cityStats: [
    { name: "Bogotá", count: 420, percent: 85 },
    { name: "Medellín", count: 310, percent: 65 },
    { name: "Cali", count: 190, percent: 45 },
    { name: "Barranquilla", count: 140, percent: 35 },
    { name: "Bucaramanga", count: 90, percent: 20 }
  ],

  tasks: [
    {
      id: "t1",
      title: "Actualizar inventario de computadores M3",
      description: "Verificar stock empresarial y renovar garantía en Supabase.",
      priority: "ALTA",
      status: "PENDIENTE"
    },
    {
      id: "t2",
      title: "Revisar facturación electrónica mensual",
      description: "Generar reporte PDF para cliente TechCorp Colombia.",
      priority: "MEDIA",
      status: "COMPLETADA"
    }
  ]
};

// Helper: Format COP Currency
function formatCOP(amount) {
  return new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    maximumFractionDigits: 0
  }).format(amount);
}

// DOM Loaded Initialization
document.addEventListener('DOMContentLoaded', async () => {
  initEvents();
  await loadDataFromSupabase();
  renderAllViews();
});

// Load real data from Supabase, fallback to local state if unavailable
async function loadDataFromSupabase() {
  try {
    const [listingsData, companiesData, invoicesData] = await Promise.all([
      window.supabaseService.getListings(),
      window.supabaseService._request('companies?select=*&order=created_at.desc'),
      window.supabaseService._request('invoices?select=*&order=created_at.desc')
    ]);

    if (listingsData && listingsData.length > 0) {
      // Map Supabase column names to local state format
      state.listings = listingsData.map(l => ({
        id: l.id,
        title: l.title,
        description: l.description,
        price: parseFloat(l.price),
        location: l.location,
        type: l.type,
        category: l.category,
        icon: categoryToIcon(l.category),
        status: l.status === 'DISPONIBLE' ? 'Disponible' : l.status === 'EN_RENTA' ? 'En Renta' : 'Vendido',
        viewsCount: l.views_count || 0,
        dateAdded: l.created_at ? l.created_at.split('T')[0] : new Date().toISOString().split('T')[0],
        sellerName: l.seller_name,
        sellerEmail: l.seller_email,
        sellerPhone: l.seller_phone,
        isSaved: false,
        isFeatured: l.is_featured || false,
        areaSqM: l.area_sqm || null,
        bedrooms: l.bedrooms || null,
        bathrooms: l.bathrooms || null,
        garage: l.garage || null,
        rating: l.rating || null
      }));
    }

    if (companiesData && companiesData.length > 0) {
      state.companies = companiesData.map(c => ({
        id: c.id,
        name: c.name,
        category: c.category,
        description: c.description || '',
        address: c.address || '',
        city: c.city,
        website: c.website || '',
        email: c.email,
        phone: c.phone,
        schedule: c.schedule || '',
        rating: parseFloat(c.rating) || 5.0,
        productsCount: 0,
        servicesCount: 0
      }));
    }

    if (invoicesData && invoicesData.length > 0) {
      state.invoices = invoicesData.map(inv => ({
        id: inv.id,
        number: inv.number,
        clientName: inv.client_name,
        clientEmail: inv.client_email,
        date: inv.date,
        status: inv.status === 'PAGADA' ? 'Pagada' : inv.status === 'VENCIDA' ? 'Vencida' : 'Pendiente',
        amount: parseFloat(inv.amount),
        description: inv.description
      }));
    }
  } catch (err) {
    console.warn('Supabase no disponible, usando datos locales:', err.message);
  }
}

function categoryToIcon(category) {
  const map = {
    'Computadores': 'fa-laptop', 'Celulares': 'fa-mobile-screen-button',
    'Consolas': 'fa-gamepad', 'Vehículos': 'fa-car',
    'Apartamentos': 'fa-building', 'Casas': 'fa-house-chimney',
    'Oficinas': 'fa-briefcase', 'Desarrollo Web': 'fa-code',
    'Diseño Gráfico': 'fa-pen-nib', 'Fotografía': 'fa-camera'
  };
  return map[category] || 'fa-tag';
}

function initEvents() {
  // Navigation & Drawer
  const drawer = document.getElementById('drawer');
  const drawerOverlay = document.getElementById('drawerOverlay');
  
  document.getElementById('drawerToggleBtn').addEventListener('click', () => {
    drawer.classList.add('active');
    drawerOverlay.classList.add('active');
  });

  const closeDrawer = () => {
    drawer.classList.remove('active');
    drawerOverlay.classList.remove('active');
  };

  document.getElementById('drawerCloseBtn').addEventListener('click', closeDrawer);
  drawerOverlay.addEventListener('click', closeDrawer);

  // Route switches from drawer and bottom nav
  document.querySelectorAll('[data-route]').forEach(el => {
    el.addEventListener('click', (e) => {
      e.preventDefault();
      const route = el.getAttribute('data-route');
      navigateTo(route);
      closeDrawer();
    });
  });

  // Brand click -> home
  document.getElementById('brandHomeBtn').addEventListener('click', () => navigateTo('home'));

  // Theme Toggle
  const themeBtn = document.getElementById('themeToggleBtn');
  const themeIcon = document.getElementById('themeIcon');
  themeBtn.addEventListener('click', () => {
    state.theme = state.theme === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', state.theme);
    themeIcon.className = state.theme === 'dark' ? 'fa-solid fa-moon' : 'fa-solid fa-sun';
  });

  // Role selector
  document.getElementById('userRoleSelect').addEventListener('change', (e) => {
    state.role = e.target.value;
    document.getElementById('dashboardTitle').textContent = 
      state.role === 'ADMINISTRADOR' ? 'Panel de Administración' : 'Mi Panel Empresarial';
  });

  // Global search input
  const searchInput = document.getElementById('globalSearchInput');
  searchInput.addEventListener('input', (e) => {
    state.searchQuery = e.target.value.toLowerCase().trim();
    renderMarketplace();
    renderRealEstate();
    renderServices();
    renderCompanies();
  });

  // Hero action buttons
  document.getElementById('heroExploreBtn').addEventListener('click', () => navigateTo('marketplace'));
  document.getElementById('heroCreateBtn').addEventListener('click', () => navigateTo('create'));

  // AI Assistant Sheet
  const fabAi = document.getElementById('fabAiBtn');
  const aiSheet = document.getElementById('aiSheet');
  const aiOverlay = document.getElementById('aiSheetOverlay');
  const closeAi = document.getElementById('closeAiSheetBtn');

  const toggleAi = (open) => {
    if (open) {
      aiSheet.classList.add('active');
      aiOverlay.classList.add('active');
    } else {
      aiSheet.classList.remove('active');
      aiOverlay.classList.remove('active');
    }
  };

  fabAi.addEventListener('click', () => toggleAi(true));
  closeAi.addEventListener('click', () => toggleAi(false));
  aiOverlay.addEventListener('click', () => toggleAi(false));

  // AI Chat send
  document.getElementById('sendAiChatBtn').addEventListener('click', handleAiChatSend);
  document.getElementById('aiChatInput').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') handleAiChatSend();
  });

  // AI Auto-generator in Form
  document.getElementById('generateWithAiBtn').addEventListener('click', handleAiFormGenerator);

  // Form Submit - Crear anuncio
  document.getElementById('createListingForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const listingData = {
      title: document.getElementById('formTitle').value,
      type: document.getElementById('formType').value,
      category: document.getElementById('formCategory').value,
      price: parseFloat(document.getElementById('formPrice').value),
      location: document.getElementById('formLocation').value,
      description: document.getElementById('formDescription').value,
      seller_name: document.getElementById('formSellerName').value,
      seller_phone: document.getElementById('formSellerPhone').value,
      seller_email: 'contacto@surgir.co',
      status: 'DISPONIBLE',
      views_count: 1,
      is_featured: true
    };
    try {
      const saved = await window.supabaseService.createListing(listingData);
      const newListing = saved[0] || listingData;
      state.listings.unshift({
        id: newListing.id || ('m' + Date.now()),
        title: listingData.title,
        type: listingData.type,
        category: listingData.category,
        price: listingData.price,
        location: listingData.location,
        description: listingData.description,
        sellerName: listingData.seller_name,
        sellerPhone: listingData.seller_phone,
        sellerEmail: listingData.seller_email,
        status: 'Disponible',
        viewsCount: 1,
        dateAdded: new Date().toISOString().split('T')[0],
        icon: categoryToIcon(listingData.category),
        isSaved: false,
        isFeatured: true
      });
    } catch (err) {
      console.warn('Error guardando en Supabase, guardando localmente:', err.message);
      state.listings.unshift({
        id: 'm' + Date.now(),
        title: listingData.title,
        type: listingData.type,
        category: listingData.category,
        price: listingData.price,
        location: listingData.location,
        description: listingData.description,
        sellerName: listingData.seller_name,
        sellerPhone: listingData.seller_phone,
        sellerEmail: listingData.seller_email,
        status: 'Disponible',
        viewsCount: 1,
        dateAdded: new Date().toISOString().split('T')[0],
        icon: categoryToIcon(listingData.category),
        isSaved: false,
        isFeatured: true
      });
    }
    alert('¡Anuncio publicado con éxito en SURGIR WEB!');
    document.getElementById('createListingForm').reset();
    renderAllViews();
    navigateTo('home');
  });

  // Modal Closers
  document.getElementById('closeDetailModalBtn').addEventListener('click', () => closeModal('detailModalOverlay', 'detailModal'));
  document.getElementById('closeContactModalBtn').addEventListener('click', () => closeModal('contactModalOverlay', 'contactModal'));
  document.getElementById('closeInvoiceModalBtn').addEventListener('click', () => closeModal('invoiceModalOverlay', 'invoiceModal'));

  // Contact Form Submit
  document.getElementById('contactForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const listingId = document.getElementById('contactListingId').value;
    const name = document.getElementById('contactName').value;
    const email = document.getElementById('contactEmail').value;
    const phone = document.getElementById('contactPhone').value;
    const msg = document.getElementById('contactMessage').value;
    const listing = state.listings.find(l => l.id === listingId);
    try {
      await window.supabaseService.sendMessage(
        listingId, name, email, phone, msg
      );
      // Add to local state
      state.messages.unshift({
        id: 'msg' + Date.now(),
        listingTitle: listing ? listing.title : 'Anuncio SURGIR',
        senderName: name,
        senderEmail: email,
        messageText: msg,
        dateSent: new Date().toLocaleString('es-CO')
      });
      renderDashboard();
    } catch (err) {
      console.warn('Error enviando mensaje a Supabase:', err.message);
    }
    alert('Mensaje enviado directamente al vendedor. Recibiás respuesta a tu correo.');
    closeModal('contactModalOverlay', 'contactModal');
  });
}

function navigateTo(route) {
  state.currentRoute = route;

  // Update active view
  document.querySelectorAll('.view').forEach(v => v.classList.remove('active-view'));
  const targetView = document.getElementById(`view-${route}`);
  if (targetView) targetView.classList.add('active-view');

  // Update active links
  document.querySelectorAll('[data-route]').forEach(el => {
    if (el.getAttribute('data-route') === route) {
      el.classList.add('active');
    } else {
      el.classList.remove('active');
    }
  });

  window.scrollTo({ top: 0, behavior: 'smooth' });
}

function renderAllViews() {
  renderHome();
  renderMarketplace();
  renderRealEstate();
  renderServices();
  renderCompanies();
  renderAnalytics();
  renderInvoices();
  renderTasks();
  renderDashboard();
}

/* Render Home */
function renderHome() {
  const featuredGrid = document.getElementById('homeFeaturedGrid');
  const featured = state.listings.filter(l => l.isFeatured).slice(0, 4);
  featuredGrid.innerHTML = featured.map(createCardHTML).join('');
  attachCardEvents(featuredGrid);

  const companiesGrid = document.getElementById('homeCompaniesGrid');
  companiesGrid.innerHTML = state.companies.map(createCompanyCardHTML).join('');

  document.getElementById('viewAllListingsBtn').onclick = () => navigateTo('marketplace');
  document.getElementById('viewAllCompaniesBtn').onclick = () => navigateTo('companies');
}

/* Render Marketplace */
function renderMarketplace() {
  const grid = document.getElementById('marketplaceGrid');
  let items = state.listings.filter(l => l.type === 'MARKETPLACE');

  if (state.searchQuery) {
    items = items.filter(i => i.title.toLowerCase().includes(state.searchQuery) || i.category.toLowerCase().includes(state.searchQuery));
  }

  grid.innerHTML = items.map(createCardHTML).join('');
  attachCardEvents(grid);
}

/* Render Real Estate */
function renderRealEstate() {
  const grid = document.getElementById('realEstateGrid');
  let items = state.listings.filter(l => l.type === 'INMOBILIARIA');

  if (state.searchQuery) {
    items = items.filter(i => i.title.toLowerCase().includes(state.searchQuery) || i.location.toLowerCase().includes(state.searchQuery));
  }

  grid.innerHTML = items.map(createCardHTML).join('');
  attachCardEvents(grid);
}

/* Render Services */
function renderServices() {
  const grid = document.getElementById('servicesGrid');
  let items = state.listings.filter(l => l.type === 'SERVICIOS');

  if (state.searchQuery) {
    items = items.filter(i => i.title.toLowerCase().includes(state.searchQuery) || i.category.toLowerCase().includes(state.searchQuery));
  }

  grid.innerHTML = items.map(createCardHTML).join('');
  attachCardEvents(grid);
}

/* Render Companies */
function renderCompanies() {
  const grid = document.getElementById('companiesDirectoryGrid');
  grid.innerHTML = state.companies.map(createCompanyCardHTML).join('');
}

/* Render Analytics */
function renderAnalytics() {
  const container = document.getElementById('cityBarsContainer');
  container.innerHTML = state.cityStats.map(c => `
    <div class="city-row">
      <span class="city-name">${c.name}</span>
      <div class="bar-wrapper">
        <div class="bar-fill" style="width: ${c.percent}%;"></div>
      </div>
      <span class="city-count">${c.count}</span>
    </div>
  `).join('');
}

/* Render Invoices */
function renderInvoices() {
  const list = document.getElementById('invoicesList');
  list.innerHTML = state.invoices.map(inv => `
    <div class="invoice-item">
      <div>
        <strong style="font-size:15px;">${inv.number}</strong>
        <p style="font-size:12px; color:var(--text-muted);">${inv.clientName} &bull; ${inv.date}</p>
        <p style="font-size:12px; margin-top:4px;">${inv.description}</p>
      </div>
      <div style="text-align:right;">
        <div style="font-weight:800; font-size:16px;">${formatCOP(inv.amount)}</div>
        <span class="invoice-status status-${inv.status.toLowerCase()}">${inv.status}</span>
        <button class="btn btn-outline" style="margin-top:8px; padding:4px 10px; font-size:11px;" onclick="viewInvoicePdf('${inv.id}')">
          <i class="fa-solid fa-file-pdf"></i> PDF
        </button>
      </div>
    </div>
  `).join('');
}

/* Render Dashboard */
function renderDashboard() {
  const userListingsWidget = document.getElementById('userListingsWidget');
  userListingsWidget.innerHTML = state.listings.slice(0, 3).map(l => `
    <div style="padding:10px 0; border-bottom:1px solid var(--border-color); display:flex; justify-content:space-between; align-items:center;">
      <div>
        <strong>${l.title}</strong>
        <p style="font-size:11px; color:var(--text-muted);">${l.location} &bull; ${formatCOP(l.price)}</p>
      </div>
      <span class="badge-tag" style="position:static;">${l.status}</span>
    </div>
  `).join('');

  const userMessagesWidget = document.getElementById('userMessagesWidget');
  userMessagesWidget.innerHTML = state.messages.map(m => `
    <div style="padding:10px 0; border-bottom:1px solid var(--border-color);">
      <div style="display:flex; justify-content:space-between; font-size:12px;">
        <strong>${m.senderName} (${m.listingTitle})</strong>
        <span style="color:var(--text-muted); font-size:10px;">${m.dateSent}</span>
      </div>
      <p style="font-size:12px; color:var(--text-muted); margin-top:4px;">"${m.messageText}"</p>
    </div>
  `).join('');
}

/* Card HTML Builders */
function createCardHTML(l) {
  return `
    <div class="card" data-id="${l.id}">
      <div class="card-header-img">
        <i class="fa-solid ${l.icon || 'fa-box'}"></i>
        <span class="badge-tag">${l.category}</span>
        <button class="save-btn ${l.isSaved ? 'active' : ''}" data-action="save" data-id="${l.id}">
          <i class="fa-solid fa-heart"></i>
        </button>
      </div>
      <div class="card-body">
        <h3 class="card-title">${l.title}</h3>
        <div class="card-price">${formatCOP(l.price)}</div>
        <div class="card-meta">
          <span><i class="fa-solid fa-location-dot"></i> ${l.location}</span>
          <span><i class="fa-solid fa-eye"></i> ${l.viewsCount}</span>
        </div>
        <p class="card-desc">${l.description}</p>
        <div class="card-footer">
          <button class="btn btn-outline" style="flex:1;" data-action="detail" data-id="${l.id}">Ver Detalle</button>
          <button class="btn btn-primary" data-action="contact" data-id="${l.id}">Contactar</button>
        </div>
      </div>
    </div>
  `;
}

function createCompanyCardHTML(c) {
  return `
    <div class="company-card">
      <div class="company-head">
        <div class="company-logo">S</div>
        <div>
          <h3 style="font-size:16px;">${c.name}</h3>
          <span style="font-size:12px; color:var(--primary); font-weight:600;">${c.category}</span>
        </div>
      </div>
      <p style="font-size:13px; color:var(--text-muted); line-height:1.5;">${c.description}</p>
      <div style="font-size:12px; color:var(--text-muted);">
        <div><i class="fa-solid fa-location-dot"></i> ${c.address}, ${c.city}</div>
        <div><i class="fa-solid fa-phone"></i> ${c.phone}</div>
      </div>
      <button class="btn btn-outline" style="width:100%; margin-top:8px;">Ver Perfil Corporativo</button>
    </div>
  `;
}

function attachCardEvents(container) {
  container.querySelectorAll('[data-action="save"]').forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.stopPropagation();
      const id = btn.getAttribute('data-id');
      const item = state.listings.find(l => l.id === id);
      if (item) {
        item.isSaved = !item.isSaved;
        renderAllViews();
      }
    });
  });

  container.querySelectorAll('[data-action="detail"]').forEach(btn => {
    btn.addEventListener('click', () => {
      const id = btn.getAttribute('data-id');
      openDetailModal(id);
    });
  });

  container.querySelectorAll('[data-action="contact"]').forEach(btn => {
    btn.addEventListener('click', () => {
      const id = btn.getAttribute('data-id');
      openContactModal(id);
    });
  });
}

function openDetailModal(id) {
  const l = state.listings.find(item => item.id === id);
  if (!l) return;

  const body = document.getElementById('detailModalBody');
  body.innerHTML = `
    <div style="text-align:center; margin-bottom:16px;">
      <span class="hero-chip">${l.category}</span>
      <h2 style="font-size:22px; font-weight:800; margin-top:8px;">${l.title}</h2>
      <div style="font-size:24px; font-weight:800; color:var(--primary); margin:8px 0;">${formatCOP(l.price)}</div>
      <div style="font-size:13px; color:var(--text-muted);"><i class="fa-solid fa-location-dot"></i> ${l.location} &bull; Publicado: ${l.dateAdded}</div>
    </div>
    
    <div style="background:var(--bg-elevated); padding:16px; border-radius:var(--radius-sm); margin-bottom:16px;">
      <h4 style="font-size:14px; margin-bottom:6px;">Descripción</h4>
      <p style="font-size:13px; color:var(--text-muted); line-height:1.6;">${l.description}</p>
    </div>

    ${l.areaSqM ? `
      <div style="display:flex; justify-content:space-around; background:var(--bg-elevated); padding:12px; border-radius:var(--radius-sm); margin-bottom:16px; text-align:center; font-size:13px;">
        <div><strong>${l.areaSqM} m²</strong><br><span style="font-size:10px; color:var(--text-muted);">Área Total</span></div>
        <div><strong>${l.bedrooms}</strong><br><span style="font-size:10px; color:var(--text-muted);">Habitaciones</span></div>
        <div><strong>${l.bathrooms}</strong><br><span style="font-size:10px; color:var(--text-muted);">Baños</span></div>
        <div><strong>${l.garage}</strong><br><span style="font-size:10px; color:var(--text-muted);">Garajes</span></div>
      </div>
    ` : ''}

    <div style="border-top:1px solid var(--border-color); padding-top:16px;">
      <h4 style="font-size:14px; margin-bottom:8px;">Información del Vendedor</h4>
      <div style="font-size:13px;">
        <strong>${l.sellerName}</strong>
        <p style="color:var(--text-muted);">${l.sellerEmail} &bull; ${l.sellerPhone}</p>
      </div>
    </div>

    <button class="btn btn-primary btn-block" style="margin-top:20px;" onclick="closeModal('detailModalOverlay', 'detailModal'); openContactModal('${l.id}');">
      <i class="fa-solid fa-envelope"></i> Contactar a ${l.sellerName}
    </button>
  `;

  openModal('detailModalOverlay', 'detailModal');
}

function openContactModal(id) {
  document.getElementById('contactListingId').value = id;
  openModal('contactModalOverlay', 'contactModal');
}

window.viewInvoicePdf = function(id) {
  const inv = state.invoices.find(i => i.id === id);
  if (!inv) return;

  const content = document.getElementById('invoicePdfContent');
  content.innerHTML = `
    <div class="pdf-invoice-box">
      <div class="pdf-header">
        <div class="pdf-header-title">
          <h2>SURGIR WEB ECOSISTEMA</h2>
          <p>Facturación Electrónica DIAN &bull; NIT 901.458.990-2</p>
        </div>
        <div style="text-align:right;">
          <div class="pdf-num">${inv.number}</div>
          <p style="font-size:12px; color:#64748b;">Fecha: ${inv.date}</p>
        </div>
      </div>

      <div style="margin-bottom:20px;">
        <p style="font-size:12px; color:#64748b; font-weight:700;">CLIENTE:</p>
        <h4 style="font-size:16px;">${inv.clientName}</h4>
        <p style="font-size:13px; color:#475569;">${inv.clientEmail}</p>
      </div>

      <table style="width:100%; border-collapse:collapse; margin-bottom:20px; font-size:13px;">
        <thead>
          <tr style="background:#f1f5f9; text-align:left;">
            <th style="padding:10px;">Descripción</th>
            <th style="padding:10px; text-align:right;">Valor</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td style="padding:12px 10px; border-bottom:1px solid #e2e8f0;">${inv.description}</td>
            <td style="padding:12px 10px; border-bottom:1px solid #e2e8f0; text-align:right; font-weight:700;">${formatCOP(inv.amount)}</td>
          </tr>
        </tbody>
      </table>

      <div style="display:flex; justify-content:space-between; align-items:center; border-top:2px solid #e2e8f0; padding-top:16px;">
        <span style="font-size:12px; color:#10b981; font-weight:700;">Estado: ${inv.status.toUpperCase()}</span>
        <div style="font-size:18px; font-weight:800; color:#1e293b;">Total: ${formatCOP(inv.amount)}</div>
      </div>
    </div>
  `;

  openModal('invoiceModalOverlay', 'invoiceModal');
};

function openModal(overlayId, modalId) {
  document.getElementById(overlayId).classList.add('active');
  document.getElementById(modalId).classList.add('active');
}

function closeModal(overlayId, modalId) {
  document.getElementById(overlayId).classList.remove('active');
  document.getElementById(modalId).classList.remove('active');
}

function handleAiChatSend() {
  const input = document.getElementById('aiChatInput');
  const text = input.value.trim();
  if (!text) return;

  const container = document.getElementById('aiMessagesContainer');
  
  // Append User message
  const userDiv = document.createElement('div');
  userDiv.className = 'ai-msg user';
  userDiv.textContent = text;
  container.appendChild(userDiv);
  input.value = '';

  // Scroll down
  container.scrollTop = container.scrollHeight;

  // Bot response simulated
  setTimeout(() => {
    let reply = "Entendido. He analizado tu solicitud en la base de datos de SURGIR WEB.";
    const lower = text.toLowerCase();
    
    if (lower.includes('macbook') || lower.includes('computador')) {
      reply = "Encontré un MacBook Pro M3 Max 16\" publicado por TechCorp Colombia por $12,500,000 COP en Bogotá.";
    } else if (lower.includes('casa') || lower.includes('penthouse') || lower.includes('inmueble')) {
      reply = "Contamos con un Penthouse en Rosales ($1,850,000,000 COP) y una Casa Campestre en Llano Grande ($2,400,000,000 COP).";
    } else if (lower.includes('factura')) {
      reply = "Tienes 3 facturas registradas en el sistema. Puedes visualizar su formato PDF descargable en la sección 'Facturas'.";
    }

    const botDiv = document.createElement('div');
    botDiv.className = 'ai-msg bot';
    botDiv.textContent = reply;
    container.appendChild(botDiv);
    container.scrollTop = container.scrollHeight;
  }, 700);
}

function handleAiFormGenerator() {
  const prompt = document.getElementById('aiPromptInput').value.trim();
  if (!prompt) {
    alert('Ingresa una pequeña descripción para que la IA genere el anuncio.');
    return;
  }

  document.getElementById('formTitle').value = "MacBook Pro M3 Max 16\" Empresarial";
  document.getElementById('formCategory').value = "Computadores";
  document.getElementById('formType').value = "MARKETPLACE";
  document.getElementById('formPrice').value = 12500000;
  document.getElementById('formLocation').value = "Bogotá, D.C.";
  document.getElementById('formDescription').value = `Autogenerado por IA SURGIR: ${prompt}. Estado del equipo 10/10, garantía empresarial vigente.`;
  alert('✨ La IA ha completado automáticamente los detalles de tu publicación.');
}

/* Render Tasks & Supabase Task Management */
function renderTasks() {
  const list = document.getElementById('tasksList');
  if (!list) return;

  list.innerHTML = state.tasks.map(t => `
    <div class="invoice-item">
      <div>
        <strong style="font-size:15px;">${t.title}</strong>
        <p style="font-size:12px; color:var(--text-muted);">${t.description || 'Sin detalles'}</p>
      </div>
      <div style="text-align:right;">
        <span class="invoice-status ${t.status === 'COMPLETADA' ? 'status-pagada' : 'status-pendiente'}">
          ${t.status} (${t.priority})
        </span>
        <button class="btn btn-outline" style="margin-top:8px; padding:4px 10px; font-size:11px;" onclick="toggleTaskStatus('${t.id}')">
          <i class="fa-solid fa-check"></i> ${t.status === 'COMPLETADA' ? 'Reabrir' : 'Completar'}
        </button>
      </div>
    </div>
  `).join('');
}

window.toggleTaskStatus = async function(taskId) {
  const task = state.tasks.find(t => t.id === taskId);
  if (task) {
    const newStatus = task.status === 'COMPLETADA' ? 'PENDIENTE' : 'COMPLETADA';
    task.status = newStatus;
    renderTasks();
    try {
      await window.supabaseService.updateTaskStatus(taskId, newStatus);
    } catch (err) {
      console.warn('Error actualizando tarea en Supabase:', err.message);
    }
  }
};

// Task Form submit handler
document.addEventListener('DOMContentLoaded', () => {
  const taskForm = document.getElementById('createTaskForm');
  if (taskForm) {
    taskForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const title = document.getElementById('taskTitle').value.trim();
      const priority = document.getElementById('taskPriority').value;
      const description = document.getElementById('taskDesc').value.trim();

      if (!title) return;

      const localTask = {
        id: 't' + Date.now(),
        title,
        priority,
        description,
        status: 'PENDIENTE'
      };

      state.tasks.unshift(localTask);
      renderTasks();
      taskForm.reset();

      try {
        // Guardar en Supabase (sin user_id para modo demostración)
        const saved = await window.supabaseService._request('tasks', {
          method: 'POST',
          body: JSON.stringify({ title, priority, description, status: 'PENDIENTE' })
        });
        if (saved && saved[0]) localTask.id = saved[0].id;
      } catch (err) {
        console.warn('Error guardando tarea en Supabase:', err.message);
      }

      alert('¡Tarea agregada y sincronizada con Supabase!');
    });
  }
});


