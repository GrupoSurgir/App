---
name: supabase-agent-skills
description: Comprehensive Supabase skills for database management, Row Level Security (RLS), authentication, edge functions, vector embeddings, and real-time subscriptions.
---

# Supabase Agent Skills

This skill provides expert patterns, best practices, and guidelines for building applications with Supabase.

## Core Capabilities

1. **Database Schema & DDL Design**:
   - Primary Keys: Always use UUID (`gen_random_uuid()`) for entities.
   - Timestamps: Include `created_at TIMESTAMPTZ DEFAULT NOW()` and `updated_at TIMESTAMPTZ DEFAULT NOW()`.
   - Auto-updating Triggers: Implement `handle_updated_at()` trigger for automatic timestamp updates.

2. **Supabase Auth & Profiles**:
   - Link profiles directly to `auth.users(id)` via `ON DELETE CASCADE`.
   - Implement an automated trigger (`handle_new_user`) on `auth.users` insertion to populate `public.profiles`.

3. **Row Level Security (RLS)**:
   - Always enable RLS on every table: `ALTER TABLE public.table_name ENABLE ROW LEVEL SECURITY;`.
   - Public read policies: `CREATE POLICY "Public Read" ON public.table_name FOR SELECT USING (true);`.
   - Owner-restricted policies: `CREATE POLICY "Owner Access" ON public.table_name FOR ALL USING (auth.uid() = user_id);`.

4. **Client API & Storage**:
   - Use Supabase JS Client for Auth (`supabase.auth.signUp`, `supabase.auth.signInWithPassword`).
   - Use PostgREST filters (`.select()`, `.eq()`, `.order()`, `.single()`).
   - Handle Realtime subscriptions with `.channel()`.

5. **Best Practices**:
   - Keep migrations idempotent with `CREATE TABLE IF NOT EXISTS` and `CREATE OR REPLACE FUNCTION`.
   - Store sensitive keys safely in environment variables (`SUPABASE_URL`, `SUPABASE_ANON_KEY`, `SUPABASE_SERVICE_ROLE_KEY`).
