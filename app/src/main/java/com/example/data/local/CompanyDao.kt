package com.example.data.local

import androidx.room.*
import com.example.data.model.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {
    @Query("SELECT * FROM companies ORDER BY rating DESC")
    fun getAllCompanies(): Flow<List<Company>>

    @Query("SELECT * FROM companies WHERE isFeatured = 1 ORDER BY rating DESC")
    fun getFeaturedCompanies(): Flow<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: Company)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompanies(companies: List<Company>)
}
