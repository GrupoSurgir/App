-- ===================================================================
-- SURGIR WEB - SUPABASE DATABASE SCHEMA
-- Tablas: Usuarios (profiles), Tareas, Métricas, Anuncios (Listings), 
--         Empresas, Facturas, Mensajes y Favoritos
-- ===================================================================

-- 1. Habilitar extensión UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. TABLA DE PERFILES / USUARIOS (vinculada con Supabase Auth)
CREATE TABLE IF NOT EXISTS public.profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    email TEXT UNIQUE NOT NULL,
    full_name TEXT NOT NULL,
    avatar_url TEXT,
    role TEXT NOT NULL DEFAULT 'USUARIO' CHECK (role IN ('USUARIO', 'EMPRESA', 'ADMINISTRADOR')),
    phone TEXT,
    city TEXT,
    bio TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3. TABLA DE TAREAS (Task Management)
CREATE TABLE IF NOT EXISTS public.tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    description TEXT,
    status TEXT NOT NULL DEFAULT 'PENDIENTE' CHECK (status IN ('PENDIENTE', 'EN_PROGRESO', 'COMPLETADA')),
    priority TEXT NOT NULL DEFAULT 'MEDIA' CHECK (priority IN ('BAJA', 'MEDIA', 'ALTA')),
    due_date DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 4. TABLA DE MÉTRICAS & ESTADÍSTICAS
CREATE TABLE IF NOT EXISTS public.metrics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    metric_name TEXT NOT NULL,
    metric_value NUMERIC NOT NULL DEFAULT 0,
    city TEXT,
    recorded_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5. TABLA DE EMPRESAS (Business Directory)
CREATE TABLE IF NOT EXISTS public.companies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    description TEXT,
    address TEXT,
    city TEXT NOT NULL,
    website TEXT,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    schedule TEXT,
    rating NUMERIC(3, 2) DEFAULT 5.0,
    logo_url TEXT,
    cover_url TEXT,
    is_featured BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 6. TABLA DE ANUNCIOS / PUBLICACIONES (Marketplace, Inmobiliaria, Servicios)
CREATE TABLE IF NOT EXISTS public.listings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seller_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    location TEXT NOT NULL,
    type TEXT NOT NULL CHECK (type IN ('MARKETPLACE', 'INMOBILIARIA', 'SERVICIOS')),
    category TEXT NOT NULL,
    image_url TEXT,
    status TEXT NOT NULL DEFAULT 'DISPONIBLE' CHECK (status IN ('DISPONIBLE', 'EN_RENTA', 'VENDIDO')),
    views_count INT DEFAULT 0,
    is_featured BOOLEAN DEFAULT FALSE,
    rating NUMERIC(3, 2) DEFAULT 5.0,
    -- Campos específicos de Inmobiliaria
    area_sqm NUMERIC(10, 2) DEFAULT 0.0,
    bedrooms INT DEFAULT 0,
    bathrooms INT DEFAULT 0,
    garage INT DEFAULT 0,
    seller_name TEXT NOT NULL,
    seller_email TEXT NOT NULL,
    seller_phone TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 7. TABLA DE FACTURAS (Invoices)
CREATE TABLE IF NOT EXISTS public.invoices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    number TEXT UNIQUE NOT NULL,
    client_name TEXT NOT NULL,
    client_email TEXT NOT NULL,
    date DATE NOT NULL DEFAULT CURRENT_DATE,
    status TEXT NOT NULL DEFAULT 'PENDIENTE' CHECK (status IN ('PAGADA', 'PENDIENTE', 'VENCIDA')),
    amount NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    description TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 8. TABLA DE MENSAJES (Messages)
CREATE TABLE IF NOT EXISTS public.messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    listing_id UUID REFERENCES public.listings(id) ON DELETE CASCADE,
    sender_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    listing_title TEXT NOT NULL,
    sender_name TEXT NOT NULL,
    sender_email TEXT NOT NULL,
    sender_phone TEXT,
    message_text TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 9. TABLA DE ANUNCIOS GUARDADOS / FAVORITOS
CREATE TABLE IF NOT EXISTS public.saved_listings (
    user_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    listing_id UUID REFERENCES public.listings(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (user_id, listing_id)
);

-- ===================================================================
-- TRIGGERS Y FUNCIONES AUTOMÁTICAS
-- ===================================================================

-- Trigger para actualizar automaticamente updated_at
CREATE OR REPLACE FUNCTION public.handle_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER on_profile_updated
    BEFORE UPDATE ON public.profiles
    FOR EACH ROW EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER on_task_updated
    BEFORE UPDATE ON public.tasks
    FOR EACH ROW EXECUTE FUNCTION public.handle_updated_at();

-- Trigger para crear automaticamente el perfil cuando se registra un usuario en Supabase Auth
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO public.profiles (id, email, full_name, avatar_url, role)
    VALUES (
        NEW.id,
        NEW.email,
        COALESCE(NEW.raw_user_meta_data->>'full_name', SPLIT_PART(NEW.email, '@', 1)),
        COALESCE(NEW.raw_user_meta_data->>'avatar_url', 'https://api.dicebear.com/7.x/bottts/svg?seed=' || NEW.id),
        COALESCE(NEW.raw_user_meta_data->>'role', 'USUARIO')
    );
    RETURN NEW;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

CREATE OR REPLACE TRIGGER on_auth_user_created
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION public.handle_new_user();

-- ===================================================================
-- SEGURIDAD: ROW LEVEL SECURITY (RLS) POLICIES
-- ===================================================================

ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.tasks ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.metrics ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.companies ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.listings ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.invoices ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.messages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.saved_listings ENABLE ROW LEVEL SECURITY;

-- Politicas para Profiles
CREATE POLICY "Lectura pública de perfiles" ON public.profiles FOR SELECT USING (true);
CREATE POLICY "Usuarios pueden actualizar su propio perfil" ON public.profiles FOR UPDATE USING (auth.uid() = id);

-- Politicas para Tareas
CREATE POLICY "Usuarios gestionan sus propias tareas" ON public.tasks FOR ALL USING (auth.uid() = user_id);

-- Politicas para Anuncios (Listings)
CREATE POLICY "Lectura pública de anuncios" ON public.listings FOR SELECT USING (true);
CREATE POLICY "Usuarios autenticados crean anuncios" ON public.listings FOR INSERT WITH CHECK (auth.uid() IS NOT NULL);
CREATE POLICY "Vendedores editan sus anuncios" ON public.listings FOR UPDATE USING (auth.uid() = seller_id);
CREATE POLICY "Vendedores eliminan sus anuncios" ON public.listings FOR DELETE USING (auth.uid() = seller_id);

-- Politicas para Empresas
CREATE POLICY "Lectura pública de empresas" ON public.companies FOR SELECT USING (true);
CREATE POLICY "Propietario gestiona su empresa" ON public.companies FOR ALL USING (auth.uid() = owner_id);

-- Politicas para Facturas
CREATE POLICY "Usuarios ven sus facturas" ON public.invoices FOR SELECT USING (auth.uid() = user_id);

-- Politicas para Mensajes
CREATE POLICY "Usuarios ven sus mensajes" ON public.messages FOR SELECT USING (auth.uid() = sender_id);
CREATE POLICY "Usuarios envían mensajes" ON public.messages FOR INSERT WITH CHECK (true);

-- Politicas para Métricas
CREATE POLICY "Lectura pública de métricas" ON public.metrics FOR SELECT USING (true);

-- ===================================================================
-- DATOS INICIALES (SEED DATA DE EJEMPLO)
-- ===================================================================

INSERT INTO public.metrics (metric_name, metric_value, city) VALUES
('visitas_totales', 148500, 'Bogotá'),
('total_anuncios', 1240, 'Bogotá'),
('total_usuarios', 8920, 'Medellín'),
('volumen_ventas', 485000000, 'Bogotá'),
('anuncios_ciudad', 420, 'Bogotá'),
('anuncios_ciudad', 310, 'Medellín'),
('anuncios_ciudad', 190, 'Cali'),
('anuncios_ciudad', 140, 'Barranquilla'),
('anuncios_ciudad', 90, 'Bucaramanga')
ON CONFLICT DO NOTHING;
