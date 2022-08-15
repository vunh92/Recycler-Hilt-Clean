package com.vunh.recycler_hilt_clean.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vunh.recycler_hilt_clean.utils.AppUtils
import com.vunh.recycler_hilt_clean.model.Movie
import com.vunh.recycler_hilt_clean.model.Response
import com.vunh.recycler_hilt_clean.model.Status
import com.vunh.recycler_hilt_clean.utils.Constant
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RecyclerService {

    @GET("pda/movie/list")
    fun callListAsync(
    ): Deferred<List<Movie>>

    @GET("pda/movie/get")
    fun callGetAsync(
        @Query("movieId") movieId: String,
    ): Deferred<Response>

    @FormUrlEncoded
    @POST("pda/movie/add")
    fun callInsertAsync(
        @Field("movieId") movieId: String,
        @Field("category") category: String,
        @Field("imageUrl") imageUrl: String,
        @Field("name") name: String,
        @Field("desc") desc: String,
    ): Deferred<Status>

    @FormUrlEncoded
    @POST("pda/movie/update")
    fun callUpdateAsync(
        @Field("movieId") movieId: String,
        @Field("category") category: String,
        @Field("imageUrl") imageUrl: String,
        @Field("name") name: String,
        @Field("desc") desc: String,
    ): Deferred<Status>

    @FormUrlEncoded
    @POST("pda/movie/delete")
    fun callDeleteAsync(
        @Field("movieId") movieId: String,
    ): Deferred<Status>

    companion object {
        var recyclerService: RecyclerService? = null

        fun getInstance() : RecyclerService {
            if (recyclerService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()

                recyclerService = retrofit.create(RecyclerService::class.java)
            }
            return recyclerService!!
        }
    }
}