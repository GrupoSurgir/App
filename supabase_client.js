/**
 * SURGIR WEB - Supabase Client Integration Module v2.0
 * Cliente completo para todas las tablas del ecosistema empresarial.
 * Tablas: profiles, listings, tasks, companies, invoices, messages,
 *         notifications, reviews, categories, user_settings,
 *         search_history, subscription_plans, user_subscriptions,
 *         user_sessions, saved_listings, metrics
 */

// Credenciales del proyecto activo (mnvwdlwzpxgyuwnhwhwj - App Web)
const SUPABASE_URL = window.ENV_SUPABASE_URL || 'https://mnvwdlwzpxgyuwnhwhwj.supabase.co';
const SUPABASE_ANON_KEY = window.ENV_SUPABASE_ANON_KEY ||
  'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1udndkbHd6cHhneXV3bmh3aHdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODU0NDYxNzAsImV4cCI6MjEwMTAyMjE3MH0.BGgZdyHSJyurzmNwV5rKOaBkwjbbmIPy9F9YRseZxoc';

class SupabaseService {
  constructor() {
    this.url = SUPABASE_URL;
    this.key = SUPABASE_ANON_KEY;
    this.session = null;
    this.profile = null;
    this._currentSessionId = null;
  }

  // Helper HTTP fetch con cabeceras de Supabase
  async _request(endpoint, options = {}) {
    const headers = {
      'apikey': this.key,
      'Authorization': `Bearer ${this.session?.access_token || this.key}`,
      'Content-Type': 'application/json',
      'Prefer': 'return=representation',
      ...(options.headers || {})
    };
    const response = await fetch(`${this.url}/rest/v1/${endpoint}`, { ...options, headers });
    if (!response.ok) {
      const err = await response.json().catch(() => ({ message: response.statusText }));
      throw new Error(err.message || `Error ${response.status} en Supabase`);
    }
    const text = await response.text();
    return text ? JSON.parse(text) : [];
  }

  // ═══════════════════════════════════════════════════════
  // 1. AUTENTICACION
  // ═══════════════════════════════════════════════════════

  async signUp(email, password, fullName, role = 'USUARIO') {
    const res = await fetch(`${this.url}/auth/v1/signup`, {
      method: 'POST',
      headers: { 'apikey': this.key, 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password, data: { full_name: fullName, role } })
    });
    const data = await res.json();
    if (data.access_token) {
      this.session = data;
      this.saveSession();
      await this._loadProfile(data.user?.id);
    }
    return data;
  }

  async signIn(email, password) {
    const res = await fetch(`${this.url}/auth/v1/token?grant_type=password`, {
      method: 'POST',
      headers: { 'apikey': this.key, 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    const data = await res.json();
    if (data.access_token) {
      this.session = data;
      this.saveSession();
      await this._loadProfile(data.user?.id);
      await this._registerSession();
    }
    return data;
  }

  async signOut() {
    if (this.session) {
      await fetch(`${this.url}/auth/v1/logout`, {
        method: 'POST',
        headers: { 'apikey': this.key, 'Authorization': `Bearer ${this.session.access_token}` }
      }).catch(() => {});
      await this._closeSession();
    }
    this.session = null;
    this.profile = null;
    this.saveSession();
  }

  async resetPassword(email) {
    const res = await fetch(`${this.url}/auth/v1/recover`, {
      method: 'POST',
      headers: { 'apikey': this.key, 'Content-Type': 'application/json' },
      body: JSON.stringify({ email })
    });
    return res.json();
  }

  restoreSession() {
    try {
      const saved = localStorage.getItem('surgir_session');
      if (saved) { this.session = JSON.parse(saved); return true; }
    } catch (e) {}
    return false;
  }

  saveSession() {
    if (this.session) {
      localStorage.setItem('surgir_session', JSON.stringify(this.session));
    } else {
      localStorage.removeItem('surgir_session');
    }
  }

  isAuthenticated() { return !!this.session?.access_token; }
  get currentUserId() { return this.profile?.id || null; }
  get currentRole() { return this.profile?.role || 'USUARIO'; }

  // ═══════════════════════════════════════════════════════
  // 2. PERFILES
  // ═══════════════════════════════════════════════════════

  async _loadProfile(authUserId) {
    if (!authUserId) return;
    const rows = await this._request(`profiles?auth_user_id=eq.${authUserId}&select=*`).catch(() => []);
    if (rows.length > 0) this.profile = rows[0];
  }

  async getProfile(profileId) {
    const rows = await this._request(`profiles?id=eq.${profileId}&select=*`);
    return rows[0] || null;
  }

  async getProfileByEmail(email) {
    const rows = await this._request(`profiles?email=eq.${encodeURIComponent(email)}&select=*`);
    return rows[0] || null;
  }

  async updateProfile(profileId, updates) {
    return this._request(`profiles?id=eq.${profileId}`, {
      method: 'PATCH',
      body: JSON.stringify({ ...updates, updated_at: new Date().toISOString() })
    });
  }

  async listProfiles(limit = 50) {
    return this._request(`profiles?select=*&order=created_at.desc&limit=${limit}`);
  }

  // ═══════════════════════════════════════════════════════
  // 3. CONFIGURACION DE USUARIO
  // ═══════════════════════════════════════════════════════

  async getUserSettings(userId) {
    const rows = await this._request(`user_settings?user_id=eq.${userId}&select=*`);
    return rows[0] || null;
  }

  async saveUserSettings(userId, settings) {
    const existing = await this.getUserSettings(userId);
    if (existing) {
      return this._request(`user_settings?user_id=eq.${userId}`, {
        method: 'PATCH',
        body: JSON.stringify({ ...settings, updated_at: new Date().toISOString() })
      });
    } else {
      return this._request('user_settings', {
        method: 'POST',
        body: JSON.stringify({ user_id: userId, ...settings })
      });
    }
  }

  // ═══════════════════════════════════════════════════════
  // 4. ANUNCIOS / LISTINGS
  // ═══════════════════════════════════════════════════════

  async getListings({ type = null, category = null, search = null, limit = 50, featured = false } = {}) {
    let q = `listings?select=*&order=created_at.desc&limit=${limit}`;
    if (type) q += `&type=eq.${type}`;
    if (category) q += `&category=eq.${encodeURIComponent(category)}`;
    if (featured) q += `&is_featured=eq.true`;
    if (search) q += `&title=ilike.*${encodeURIComponent(search)}*`;
    return this._request(q);
  }

  async getListing(id) {
    const rows = await this._request(`listings?id=eq.${id}&select=*`);
    return rows[0] || null;
  }

  async createListing(data) {
    return this._request('listings', {
      method: 'POST',
      body: JSON.stringify({ ...data, seller_id: this.profile?.id || null, date_added: new Date().toISOString().split('T')[0] })
    });
  }

  async updateListing(id, updates) {
    return this._request(`listings?id=eq.${id}`, { method: 'PATCH', body: JSON.stringify(updates) });
  }

  async deleteListing(id) {
    return this._request(`listings?id=eq.${id}`, { method: 'DELETE' });
  }

  // ═══════════════════════════════════════════════════════
  // 5. FAVORITOS
  // ═══════════════════════════════════════════════════════

  async getSavedListings(userId) {
    return this._request(`saved_listings?user_id=eq.${userId}&select=*,listings(*)`);
  }

  async toggleSavedListing(userId, listingId) {
    const existing = await this._request(`saved_listings?user_id=eq.${userId}&listing_id=eq.${listingId}&select=*`);
    if (existing.length > 0) {
      await this._request(`saved_listings?user_id=eq.${userId}&listing_id=eq.${listingId}`, { method: 'DELETE' });
      return { saved: false };
    } else {
      await this._request('saved_listings', { method: 'POST', body: JSON.stringify({ user_id: userId, listing_id: listingId }) });
      return { saved: true };
    }
  }

  // ═══════════════════════════════════════════════════════
  // 6. TAREAS
  // ═══════════════════════════════════════════════════════

  async getTasks(userId, status = null) {
    let q = `tasks?user_id=eq.${userId}&order=created_at.desc`;
    if (status) q += `&status=eq.${status}`;
    return this._request(q);
  }

  async createTask(data) {
    return this._request('tasks', {
      method: 'POST',
      body: JSON.stringify({ user_id: this.profile?.id || null, status: 'PENDIENTE', ...data })
    });
  }

  async updateTaskStatus(taskId, status) {
    return this._request(`tasks?id=eq.${taskId}`, {
      method: 'PATCH',
      body: JSON.stringify({ status, updated_at: new Date().toISOString() })
    });
  }

  async updateTask(taskId, updates) {
    return this._request(`tasks?id=eq.${taskId}`, {
      method: 'PATCH',
      body: JSON.stringify({ ...updates, updated_at: new Date().toISOString() })
    });
  }

  async deleteTask(taskId) {
    return this._request(`tasks?id=eq.${taskId}`, { method: 'DELETE' });
  }

  // ═══════════════════════════════════════════════════════
  // 7. EMPRESAS
  // ═══════════════════════════════════════════════════════

  async getCompanies({ limit = 50, featured = false } = {}) {
    let q = `companies?select=*&order=rating.desc&limit=${limit}`;
    if (featured) q += `&is_featured=eq.true`;
    return this._request(q);
  }

  async getCompany(id) {
    const rows = await this._request(`companies?id=eq.${id}&select=*`);
    return rows[0] || null;
  }

  async createCompany(data) {
    return this._request('companies', {
      method: 'POST',
      body: JSON.stringify({ ...data, owner_id: this.profile?.id || null })
    });
  }

  async updateCompany(id, updates) {
    return this._request(`companies?id=eq.${id}`, { method: 'PATCH', body: JSON.stringify(updates) });
  }

  // ═══════════════════════════════════════════════════════
  // 8. FACTURAS
  // ═══════════════════════════════════════════════════════

  async getInvoices(userId = null, limit = 50) {
    let q = `invoices?select=*&order=created_at.desc&limit=${limit}`;
    if (userId) q += `&user_id=eq.${userId}`;
    return this._request(q);
  }

  async createInvoice(data) {
    return this._request('invoices', {
      method: 'POST',
      body: JSON.stringify({ ...data, user_id: this.profile?.id || null, date: data.date || new Date().toISOString().split('T')[0] })
    });
  }

  async updateInvoiceStatus(id, status) {
    return this._request(`invoices?id=eq.${id}`, { method: 'PATCH', body: JSON.stringify({ status }) });
  }

  // ═══════════════════════════════════════════════════════
  // 9. MENSAJES
  // ═══════════════════════════════════════════════════════

  async sendMessage(listingId, senderName, senderEmail, senderPhone, messageText, listingTitle = 'Anuncio SURGIR') {
    return this._request('messages', {
      method: 'POST',
      body: JSON.stringify({
        listing_id: listingId || null,
        sender_id: this.profile?.id || null,
        listing_title: listingTitle,
        sender_name: senderName,
        sender_email: senderEmail,
        sender_phone: senderPhone,
        message_text: messageText
      })
    });
  }

  async getMessages(limit = 50) {
    return this._request(`messages?select=*&order=created_at.desc&limit=${limit}`);
  }

  async markMessageRead(id) {
    return this._request(`messages?id=eq.${id}`, { method: 'PATCH', body: JSON.stringify({ is_read: true }) });
  }

  // ═══════════════════════════════════════════════════════
  // 10. NOTIFICACIONES
  // ═══════════════════════════════════════════════════════

  async getNotifications(userId, limit = 20) {
    return this._request(`notifications?user_id=eq.${userId}&order=created_at.desc&limit=${limit}`);
  }

  async markNotificationRead(id) {
    return this._request(`notifications?id=eq.${id}`, { method: 'PATCH', body: JSON.stringify({ is_read: true }) });
  }

  async markAllNotificationsRead(userId) {
    return this._request(`notifications?user_id=eq.${userId}&is_read=eq.false`, {
      method: 'PATCH', body: JSON.stringify({ is_read: true })
    });
  }

  async createNotification(userId, type, title, body, metadata = {}) {
    return this._request('notifications', {
      method: 'POST',
      body: JSON.stringify({ user_id: userId, type, title, body, metadata })
    });
  }

  // ═══════════════════════════════════════════════════════
  // 11. RESENAS
  // ═══════════════════════════════════════════════════════

  async getReviews({ listingId = null, companyId = null } = {}) {
    let q = `reviews?select=*,profiles(full_name,avatar_url)&order=created_at.desc`;
    if (listingId) q += `&listing_id=eq.${listingId}`;
    if (companyId) q += `&company_id=eq.${companyId}`;
    return this._request(q);
  }

  async createReview(data) {
    return this._request('reviews', {
      method: 'POST',
      body: JSON.stringify({ ...data, reviewer_id: this.profile?.id || null })
    });
  }

  // ═══════════════════════════════════════════════════════
  // 12. CATEGORIAS
  // ═══════════════════════════════════════════════════════

  async getCategories(section = null) {
    let q = `categories?select=*&is_active=eq.true&order=sort_order.asc`;
    if (section) q += `&section=eq.${section}`;
    return this._request(q);
  }

  // ═══════════════════════════════════════════════════════
  // 13. METRICAS
  // ═══════════════════════════════════════════════════════

  async getMetrics(metricName = null) {
    let q = `metrics?select=*&order=recorded_at.desc`;
    if (metricName) q += `&metric_name=eq.${metricName}`;
    return this._request(q);
  }

  async addMetric(metricName, metricValue, city = 'Bogota') {
    return this._request('metrics', {
      method: 'POST',
      body: JSON.stringify({ user_id: this.profile?.id || null, metric_name: metricName, metric_value: metricValue, city })
    });
  }

  // ═══════════════════════════════════════════════════════
  // 14. PLANES Y SUSCRIPCIONES
  // ═══════════════════════════════════════════════════════

  async getPlans() {
    return this._request(`subscription_plans?select=*&is_active=eq.true&order=price_monthly.asc`);
  }

  async getUserSubscription(userId) {
    const rows = await this._request(`user_subscriptions?user_id=eq.${userId}&status=eq.ACTIVA&select=*,subscription_plans(*)`);
    return rows[0] || null;
  }

  async subscribeToPlan(userId, planId, paymentMethod = 'PSE') {
    await this._request(`user_subscriptions?user_id=eq.${userId}&status=eq.ACTIVA`, {
      method: 'PATCH', body: JSON.stringify({ status: 'CANCELADA' })
    }).catch(() => {});
    const expiresAt = new Date();
    expiresAt.setMonth(expiresAt.getMonth() + 1);
    return this._request('user_subscriptions', {
      method: 'POST',
      body: JSON.stringify({ user_id: userId, plan_id: planId, status: 'ACTIVA', expires_at: expiresAt.toISOString(), payment_method: paymentMethod })
    });
  }

  // ═══════════════════════════════════════════════════════
  // 15. HISTORIAL DE BUSQUEDAS
  // ═══════════════════════════════════════════════════════

  async logSearch(query, section = null, resultsCount = 0) {
    if (!this.profile) return;
    return this._request('search_history', {
      method: 'POST',
      body: JSON.stringify({ user_id: this.profile.id, query, section, results_count: resultsCount })
    }).catch(() => {});
  }

  async getSearchHistory(userId, limit = 10) {
    return this._request(`search_history?user_id=eq.${userId}&order=searched_at.desc&limit=${limit}`);
  }

  // ═══════════════════════════════════════════════════════
  // 16. SESIONES
  // ═══════════════════════════════════════════════════════

  async _registerSession() {
    if (!this.profile) return;
    const rows = await this._request('user_sessions', {
      method: 'POST',
      body: JSON.stringify({ user_id: this.profile.id, user_agent: navigator.userAgent.slice(0, 200), country: 'Colombia', is_active: true })
    }).catch(() => []);
    if (rows[0]) this._currentSessionId = rows[0].id;
  }

  async _closeSession() {
    if (!this._currentSessionId) return;
    await this._request(`user_sessions?id=eq.${this._currentSessionId}`, {
      method: 'PATCH',
      body: JSON.stringify({ is_active: false, session_end: new Date().toISOString() })
    }).catch(() => {});
    this._currentSessionId = null;
  }
}

// Exportar instancia global
window.supabaseService = new SupabaseService();

// Restaurar sesion al cargar la pagina
window.supabaseService.restoreSession();

console.log('%c SURGIR WEB - Supabase v2.0 conectado', 'color:#6366f1;font-weight:bold;font-size:12px;');
