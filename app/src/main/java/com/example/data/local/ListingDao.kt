package com.example.data.local

import androidx.room.*
import com.example.data.model.Listing
import com.example.data.model.ListingType
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    @Query("SELECT * FROM listings ORDER BY dateAdded DESC")
    fun getAllListings(): Flow<List<Listing>>

    @Query("SELECT * FROM listings WHERE type = :type ORDER BY dateAdded DESC")
    fun getListingsByType(type: ListingType): Flow<List<Listing>>

    @Query("SELECT * FROM listings WHERE isFeatured = 1 ORDER BY dateAdded DESC")
    fun getFeaturedListings(): Flow<List<Listing>>

    @Query("SELECT * FROM listings WHERE isSaved = 1 ORDER BY dateAdded DESC")
    fun getSavedListings(): Flow<List<Listing>>

    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getListingById(id: String): Listing?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListing(listing: Listing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllListings(listings: List<Listing>)

    @Query("UPDATE listings SET isSaved = :isSaved WHERE id = :id")
    suspend fun updateSavedStatus(id: String, isSaved: Boolean)

    @Query("UPDATE listings SET viewsCount = viewsCount + 1 WHERE id = :id")
    suspend fun incrementViews(id: String)

    @Delete
    suspend fun deleteListing(listing: Listing)
}
