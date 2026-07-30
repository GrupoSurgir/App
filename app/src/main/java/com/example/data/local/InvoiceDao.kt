package com.example.data.local

import androidx.room.*
import com.example.data.model.Invoice
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM invoices ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: Invoice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvoices(invoices: List<Invoice>)
}
