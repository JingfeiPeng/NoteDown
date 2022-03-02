package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface  DocumentDao {
    @Query("SELECT * FROM document")
    fun fetchAllCustomer(): List<Document>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDocument(customer: Document)

    @Query("DELETE FROM document where id = :id")
    suspend fun deleteCustomerById(id: Int)
}