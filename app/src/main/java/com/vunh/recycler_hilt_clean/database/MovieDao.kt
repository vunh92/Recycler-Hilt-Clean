package com.vunh.recycler_hilt_clean.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vunh.recycler_hilt_clean.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Query("DELETE FROM tb_Moive WHERE movieId = :movieId")
    suspend fun delete(movieId: String)

    @Query("SELECT * from tb_Moive WHERE movieId = :movieId")
    suspend fun get(movieId: String): Movie

    @Query("DELETE FROM tb_Moive")
    suspend fun clear()

//    @Query("SELECT * FROM tb_Moive ORDER BY dateUpdated ASC")
    @Query("SELECT * FROM tb_Moive ORDER BY movieId DESC")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM tb_Moive WHERE movieId = :movieId")
    suspend fun getMovie(movieId: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( movies: List<Movie>)
}