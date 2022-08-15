package com.vunh.recycler_hilt_clean.repository

import androidx.lifecycle.LiveData
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean.model.Response
import com.vunh.recycler_hilt_clean.model.Status
import com.vunh.recycler_hilt_clean.usecase.UseCaseResult

interface RecyclerRepository {
    suspend fun getMovieList(): UseCaseResult<List<Movie>>
    suspend fun getMovie(movieId: String): UseCaseResult<Response>
    suspend fun insert(movie: Movie) : UseCaseResult<Status>
    suspend fun update(movie: Movie) : UseCaseResult<Status>
    suspend fun delete(movieId: String) : UseCaseResult<Status>
    suspend fun clear()
    suspend fun insertAll(movies: List<Movie>)
    val movies: LiveData<List<Movie>>
}