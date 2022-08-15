package com.vunh.recycler_hilt_clean_clean.viewModel.create_movie

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean.repository.RecyclerRepositoryImpl
import com.vunh.recycler_hilt_clean.usecase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CreateMovieViewModel @Inject constructor(
    private val recyclerRepositoryImpl: RecyclerRepositoryImpl
    ) : ViewModel() , CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    val showResult= MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun insertMovie(movie: Movie) {
        showLoading.value = true
        viewModelScope.launch {
            try {
                val currentTimestamp = System.currentTimeMillis()
                movie.movieId = "movieId_$currentTimestamp"
                var result = withContext(Dispatchers.IO){
                    recyclerRepositoryImpl.insert(movie)
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        showResult.value = !result.data.error
                    }
                    is UseCaseResult.Error -> {
                        showResult.value = false
                        showError.value = result.errorMessage
                    }
                }
            } catch (networkError: IOException) {
                showLoading.value= false
                showResult.value = false
            }
        }
    }

    fun updateMovie(movie: Movie) {
        showLoading.value = true
        viewModelScope.launch {
            try {
                var result = withContext(Dispatchers.IO){
                    recyclerRepositoryImpl.update(movie)
                }
                showLoading.value= false
                when (result) {
                    is UseCaseResult.Success -> {
                        showResult.value = !result.data.error
                    }
                    is UseCaseResult.Error -> {
                        showResult.value = false
                        showError.value = result.errorMessage
                    }
                }
            } catch (networkError: IOException) {
                showLoading.value= false
                showResult.value = false
            }
        }
    }

    fun finishActivity(activity: Activity) {
        viewModelScope.launch {
            delay(2000L)
            activity.finish()
        }
    }
}